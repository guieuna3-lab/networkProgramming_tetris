package tetris.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 클라이언트 한 명을 전담하는 스레드.
 *
 * readLine()은 메시지가 올 때까지 멈춰 있으므로(블로킹),
 * 클라이언트마다 스레드를 따로 두어야 한 명이 조용해도 다른 사람의 메시지를 처리할 수 있다.
 */
public class ClientHandler extends Thread {

    private final GameServer server;
    private final Socket socket;
    private PrintWriter out;

    private int playerId;
    private String playerName;
    private GameRoom room;

    public ClientHandler(GameServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

            String line;
            while ((line = in.readLine()) != null) {
                handle(line);
            }
            // readLine()이 null이면 상대가 정상적으로 연결을 닫은 것이다.
            GameServer.log("연결 종료: " + who());
        } catch (IOException e) {
            GameServer.log("연결 끊김: " + who());
        } finally {
            server.leave(this);
            try {
                socket.close();
            } catch (IOException e) {
                // 닫는 중에 난 오류는 더 할 수 있는 일이 없으므로 무시한다.
            }
        }
    }

    /** 받은 한 줄("/종류 값1 값2 …")을 종류에 따라 처리한다. */
    private void handle(String line) {
        String[] parts = line.split(" ", 2);
        String type = parts[0];
        String rest = parts.length > 1 ? parts[1] : "";

        // 입장 전에는 /JOIN만 받는다.
        if (room == null && !type.equals("/JOIN")) {
            send("/ERROR 입장전");
            return;
        }

        switch (type) {
            case "/JOIN" -> handleJoin(rest);
            // 다음 주에 /BOARD 등을 여기에 추가한다.
            default -> GameServer.log("알 수 없는 메시지 (" + who() + "): " + line);
        }
    }

    private void handleJoin(String name) {
        if (room != null) {
            return; // 이미 입장했으면 무시
        }
        String reason = server.join(this, name);
        if (reason != null) {
            send("/JOIN_FAIL " + reason);
        }
    }

    /**
     * 이 클라이언트에게 한 줄을 보낸다.
     * 상대방의 스레드도 이 메서드를 호출할 수 있으므로(예: /START) synchronized로 보호한다.
     */
    public synchronized void send(String message) {
        out.println(message);
    }

    /** 로그에 표시할 이름. 입장 전이면 주소를 쓴다. */
    private String who() {
        return playerName != null ? playerName : String.valueOf(socket.getRemoteSocketAddress());
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public GameRoom getRoom() {
        return room;
    }

    public void setRoom(GameRoom room) {
        this.room = room;
    }
}
