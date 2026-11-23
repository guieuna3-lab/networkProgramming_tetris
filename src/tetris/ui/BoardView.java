
package tetris.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import tetris.game.Board;
import tetris.game.Piece;

public class BoardView extends JPanel {

    private final Board board;
    private final int cellSize;

    private final Color[] colors = {
        Color.BLACK,
        Color.CYAN,
        Color.YELLOW,
        Color.MAGENTA,
        Color.GREEN,
        Color.RED,
        Color.BLUE,
        Color.ORANGE
    };

    public BoardView(Board board, int cellSize) {
        this.board = board;
        this.cellSize = cellSize;

        setPreferredSize(new Dimension(10 * cellSize, 20 * cellSize));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[][] cells = board.getCells();

        // 게임판에 저장된 블록 그리기
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                drawCell(g, x, y, cells[y][x]);
            }
        }

        // 현재 떨어지는 블록 그리기
        Piece piece = board.getCurrentPiece();

        if (piece != null) {
            int[][] shape = piece.getShape();

            for (int y = 0; y < shape.length; y++) {
                for (int x = 0; x < shape[y].length; x++) {
                    if (shape[y][x] != 0) {
                        drawCell(g,
                            board.getPieceX() + x,
                            board.getPieceY() + y,
                            piece.getType());
                    }
                }
            }
        }
    }

    private void drawCell(Graphics g, int x, int y, int type) {
        int px = x * cellSize;
        int py = y * cellSize;

        g.setColor(colors[type]);
        g.fillRect(px, py, cellSize, cellSize);

        g.setColor(Color.DARK_GRAY);
        g.drawRect(px, py, cellSize, cellSize);
    }
}
