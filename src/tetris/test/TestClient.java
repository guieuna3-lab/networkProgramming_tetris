package tetris.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 서버 확인용 콘솔 클라이언트.
 * 화면 없이 Socket으로 직접 접속해서, 보낸 줄과 받은 줄을 그대로 출력한다.
 */
public class TestClient {

    private final String label;
    private final Socket socket;
    private final PrintWriter out;

    /** 서버에 접속하고, 받은 줄을 출력하는 수신 전용 스레드를 시작한다. */
    public TestClient(String label, int port) throws IOException {
        this.label = label;

        socket = new Socket("localhost", port);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

        Thread receiver = new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("  [" + label + " 수신] " + line);
                }
            } catch (IOException e) {
                // 테스트가 끝나 연결이 닫힌 경우이므로 조용히 끝낸다.
            }
        });
        receiver.setDaemon(true);
        receiver.start();
    }

    /** 한 줄을 보내고, 서버의 응답이 출력될 시간을 잠깐 기다린다. */
    public void send(String msg) throws InterruptedException {
        System.out.println("  [" + label + " 송신] " + msg);
        out.println(msg);
        Thread.sleep(150);
    }

    /** 연결을 닫고, 서버가 퇴장 처리를 마칠 시간을 잠깐 기다린다. */
    public void close() throws IOException, InterruptedException {
        System.out.println("  [" + label + " 연결 종료]");
        socket.close();
        Thread.sleep(150);
    }
}
