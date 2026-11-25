
package tetris.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.*;

import tetris.game.Board;
import tetris.game.Piece;

public class GameFrame extends JFrame {

    private final Board myBoard = new Board();
    private final Board opponentBoard = new Board();

    private final BoardView myView = new BoardView(myBoard, 24);
    private final BoardView opponentView = new BoardView(opponentBoard, 17);

    private final JLabel statusLabel = new JLabel("상대를 기다리는 중", SwingConstants.CENTER);
    private final JLabel myScoreLabel = new JLabel("점수: 0");
    private final JLabel opponentScoreLabel = new JLabel("점수: 0");
    private final JLabel nextLabel = new JLabel("NEXT: -");

    private final JTextArea chatArea = new JTextArea(4, 30);
    private final JTextField chatInput = new JTextField(20);

    private final Random random = new Random();

    private Timer timer;

    public GameFrame() {
        setTitle("2인 대전 테트리스");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 상단 상태 표시
        add(statusLabel, BorderLayout.NORTH);

        // 내 게임판
        JPanel myPanel = new JPanel(new BorderLayout());
        myPanel.add(new JLabel("내 게임판", SwingConstants.CENTER), BorderLayout.NORTH);
        myPanel.add(myView, BorderLayout.CENTER);

        JPanel myInfo = new JPanel(new GridLayout(2, 1));
        myInfo.add(nextLabel);
        myInfo.add(myScoreLabel);
        myPanel.add(myInfo, BorderLayout.EAST);

        // 상대 게임판
        JPanel opponentPanel = new JPanel(new BorderLayout());
        opponentPanel.add(new JLabel("상대 게임판", SwingConstants.CENTER), BorderLayout.NORTH);
        opponentPanel.add(opponentView, BorderLayout.CENTER);

        JPanel opponentInfo = new JPanel(new GridLayout(2, 1));
        opponentInfo.add(new JLabel("NEXT: -"));
        opponentInfo.add(opponentScoreLabel);
        opponentPanel.add(opponentInfo, BorderLayout.EAST);

        // 가운데 게임 영역
        JPanel gamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        gamePanel.add(myPanel);
        gamePanel.add(new JLabel("VS"));
        gamePanel.add(opponentPanel);

        add(gamePanel, BorderLayout.CENTER);

        // 하단 채팅 영역
        JPanel chatPanel = new JPanel(new BorderLayout());

        chatArea.setEditable(false);
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(chatInput, BorderLayout.CENTER);

        JButton sendButton = new JButton("전송");
        inputPanel.add(sendButton, BorderLayout.EAST);

        chatPanel.add(inputPanel, BorderLayout.SOUTH);
        add(chatPanel, BorderLayout.SOUTH);

        // 블록 낙하 타이머
        timer = new Timer(500, e -> {
            if (!myBoard.move(0, 1)) {
                timer.stop();
            }
            myView.repaint();
        });

        pack();
        setLocationRelativeTo(null);
    }

    // 네트워크 연결 전, 혼자 화면을 확인하기 위한 실행
    public void startTest() {
        int type = random.nextInt(7) + 1;
        myBoard.spawn(new Piece(type));

        statusLabel.setText("게임 중 (테스트)");
        myView.repaint();
        timer.start();
    }
}
