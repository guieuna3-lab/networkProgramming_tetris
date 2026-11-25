
package tetris.test;

import javax.swing.SwingUtilities;
import tetris.ui.GameFrame;

public class GameFrameTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
            frame.startTest();
        });
    }
}
