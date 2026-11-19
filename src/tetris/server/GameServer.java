package tetris.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tetris.common.Protocol;

/**
 * 게임 서버.
 *
 * 수업 시간의 다중 클라이언트 서버 구조를 그대로 따른다.
 *  - accept()를 반복하면서 접속을 기다리고
 *  - 접속한 클라이언트마다 스레드(ClientHandler)를 하나씩 만들고
 *  - 여러 스레드가 함께 쓰는 접속자 정보는 synchronized로 보호한다.
 *
 * 실행 인자: [port] (생략 시 Protocol.DEFAULT_PORT = 5000)
 */
public class GameServer {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    /** 사용 중인 닉네임 목록 (중복 검사용) */
    private final Set<String> names = new HashSet<>();

    /** 만들어진 게임방 목록 */
    private final List<GameRoom> rooms = new ArrayList<>();

    /** 다음에 배정할 플레이어 ID (1부터 증가) */
    private int nextPlayerId = 1;

    /** 다음에 배정할 게임방 ID (1부터 증가) */
    private int nextRoomId = 1;

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : Protocol.DEFAULT_PORT;
        new GameServer().start(port);
    }

    /** 포트를 열고 접속을 계속 받는다. 접속마다 전담 스레드를 만든다. */
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log("서버 시작 (포트 " + port + ")");

            while (true) {
                // accept()는 누군가 접속할 때까지 여기서 멈춰 있다(블로킹).
                Socket socket = serverSocket.accept();
                log("클라이언트 접속: " + socket.getRemoteSocketAddress());

                // 이 클라이언트의 메시지는 전담 스레드가 읽고,
                // 메인 스레드는 곧바로 다음 접속을 받으러 돌아간다.
                new ClientHandler(this, socket).start();
            }
        } catch (IOException e) {
            log("서버 오류: " + e.getMessage());
        }
    }

    /** "[서버 HH:mm:ss] 메시지" 형식으로 로그를 출력한다. */
    public static void log(String message) {
        System.out.println("[서버 " + LocalTime.now().format(TIME_FORMAT) + "] " + message);
    }

    /**
     * 입장 처리. 실패하면 사유를, 성공하면 null을 돌려준다.
     *
     * synchronized인 이유: 닉네임 중복 검사와 등록, ID 배정, 방 배정이
     * 여러 ClientHandler 스레드에서 동시에 실행되면 안 되기 때문이다.
     * (예: 두 명이 동시에 같은 닉네임으로 들어오면 둘 다 "중복 아님"으로 통과할 수 있다.)
     */
    public synchronized String join(ClientHandler c, String name) {
        if (name == null || name.isEmpty() || name.contains(" ")) {
            return "닉네임형식오류";
        }
        if (names.contains(name)) {
            return "닉네임중복";
        }

        // 닉네임 등록 → 플레이어 ID 배정
        names.add(name);
        c.setPlayerName(name);
        c.setPlayerId(nextPlayerId++);

        // 대기 중인 게임방 배정
        GameRoom room = findWaitingRoom();
        log(name + " → 플레이어 " + c.getPlayerId() + ", 게임방 " + room.getId()
                + " 입장 (" + (room.size() + 1) + "/" + Protocol.MAX_PLAYERS + ")");

        // ① 먼저 ID를 알려주고
        c.send("/JOIN_OK " + c.getPlayerId());
        // ② 그 다음 방에 넣는다 (2명이면 START)
        room.add(c);
        return null;
    }

    /**
     * 퇴장 처리. 연결이 끝난 클라이언트의 닉네임과 방 자리를 정리한다.
     * (14주차 전까지 개발 편의용 최소 처리. 남은 사람에게 알리는 것은 14주차에 추가한다.)
     */
    public synchronized void leave(ClientHandler c) {
        if (c.getPlayerName() == null) {
            return; // 입장한 적이 없으면 정리할 것이 없다.
        }
        names.remove(c.getPlayerName());

        GameRoom room = c.getRoom();
        if (room != null) {
            room.leave(c);
            if (room.size() == 0) {
                rooms.remove(room);
            }
        }
    }

    /** 빈자리(1명)가 있는 방을 찾고, 없으면 새 방을 만든다. join() 안에서만 호출한다. */
    private GameRoom findWaitingRoom() {
        for (GameRoom room : rooms) {
            if (!room.isFull()) {
                return room;
            }
        }
        GameRoom room = new GameRoom(nextRoomId++);
        rooms.add(room);
        return room;
    }
}
