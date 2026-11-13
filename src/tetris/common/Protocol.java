package tetris.common;

/**
 * 서버와 클라이언트가 함께 사용하는 통신 관련 상수.
 * 메시지 형식 등 프로토콜 정의는 추후 이 패키지에 추가한다.
 */
public final class Protocol {

    /** 기본 서버 주소 (로컬 테스트용) */
    public static final String DEFAULT_HOST = "localhost";

    /** 기본 서버 포트 */
    public static final int DEFAULT_PORT = 5000;

    /** 한 게임에 참여하는 플레이어 수 */
    public static final int MAX_PLAYERS = 2;

    private Protocol() {
    }
}
