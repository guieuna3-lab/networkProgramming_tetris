package tetris.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tetris.common.Protocol;

/**
 * 서버 실행 진입점.
 *
 * 현재는 초기 환경 확인용으로, 포트를 열고 접속한 클라이언트를 로그로 출력만 한다.
 * 클라이언트 관리, 게임 진행, 메시지 중계는 추후 구현한다.
 *
 * 실행 인자: [port] (생략 시 Protocol.DEFAULT_PORT)
 */
public class ServerMain {

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : Protocol.DEFAULT_PORT;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[Server] Started on port " + port);

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("[Server] Client connected: " + client.getRemoteSocketAddress());
                // TODO: 클라이언트별 처리 스레드 생성 및 2인 매칭 (추후 구현)
                client.close();
            }
        } catch (IOException e) {
            System.err.println("[Server] Error: " + e.getMessage());
        }
    }
}
