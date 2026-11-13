package tetris.client;

import java.io.IOException;
import java.net.Socket;

import javax.swing.SwingUtilities;

import tetris.common.Protocol;
import tetris.ui.MainFrame;

/**
 * 클라이언트 실행 진입점.
 *
 * 현재는 초기 환경 확인용으로, 창을 띄우고 서버 접속 가능 여부만 표시한다.
 * 실제 서버와의 메시지 송수신은 추후 구현한다.
 *
 * 실행 인자: [host] [port] (생략 시 Protocol의 기본값)
 */
public class ClientMain {

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : Protocol.DEFAULT_HOST;
        int port = args.length > 1 ? Integer.parseInt(args[1]) : Protocol.DEFAULT_PORT;

        String status = checkConnection(host, port);
        System.out.println("[Client] " + status);

        SwingUtilities.invokeLater(() -> new MainFrame(status).setVisible(true));
    }

    /** 서버에 접속을 시도하고 결과 문자열을 돌려준다. */
    private static String checkConnection(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            return "Connected to server " + host + ":" + port;
        } catch (IOException e) {
            return "Server not available at " + host + ":" + port;
        }
    }
}
