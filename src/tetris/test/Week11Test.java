package tetris.test;

import tetris.server.GameServer;

/**
 * 11주차 확인용 테스트: 서버 골격과 입장 흐름.
 * 같은 JVM 안에서 서버(포트 5111)를 띄우고 콘솔 클라이언트 3개로 5개 시나리오를 차례로 실행한다.
 */
public class Week11Test {

    private static final int PORT = 5111;

    public static void main(String[] args) throws Exception {
        // 서버는 accept()에서 계속 멈춰 있으므로 별도 스레드에서 실행한다.
        Thread serverThread = new Thread(() -> new GameServer().start(PORT));
        serverThread.setDaemon(true);
        serverThread.start();
        Thread.sleep(300); // 서버가 포트를 열 때까지 대기

        TestClient hyunbin = new TestClient("현빈", PORT);
        TestClient sumin = new TestClient("수민", PORT);
        TestClient chulsoo = new TestClient("철수", PORT);
        Thread.sleep(150);

        title(1, "입장 전에 다른 메시지를 보내면 /ERROR 입장전");
        hyunbin.send("/CHAT 현빈 안녕");

        title(2, "현빈 입장 → /JOIN_OK 1");
        hyunbin.send("/JOIN 현빈");

        title(3, "수민이 이미 있는 닉네임으로 입장 → /JOIN_FAIL 닉네임중복");
        sumin.send("/JOIN 현빈");

        title(4, "수민 입장 → /JOIN_OK 2, 양쪽에 /START");
        sumin.send("/JOIN 수민");

        title(5, "철수 입장 → /JOIN_OK 3, 게임방 2에서 대기");
        chulsoo.send("/JOIN 철수");

        title(6, "현빈 연결 종료 → 같은 닉네임으로 재입장 → /JOIN_OK");
        hyunbin.close();
        TestClient hyunbinAgain = new TestClient("현빈", PORT);
        hyunbinAgain.send("/JOIN 현빈");

        System.out.println();
        System.out.println("== 테스트 끝");
        System.exit(0);
    }

    private static void title(int n, String description) {
        System.out.println();
        System.out.println("== 테스트 " + n + ": " + description);
    }
}
