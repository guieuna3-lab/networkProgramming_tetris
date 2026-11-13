package tetris.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * 클라이언트 메인 창.
 *
 * 현재는 상태 문구만 보여주는 빈 창이다.
 * 게임 보드, 상대방 보드, 채팅 패널은 추후 이 패키지에 추가한다.
 */
public class MainFrame extends JFrame {

    public MainFrame(String statusText) {
        super("Network Programming Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 320);
        setLocationRelativeTo(null);

        add(new JLabel(statusText, SwingConstants.CENTER));
    }
}
