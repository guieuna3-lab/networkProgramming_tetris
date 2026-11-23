
package tetris.game;

public class Board {

    private final int[][] cells = new int[20][10];

    private Piece currentPiece;
    private int pieceX;
    private int pieceY;

    // 새로운 블록 생성
    public boolean spawn(Piece piece) {
        currentPiece = piece;

        int[][] shape = piece.getShape();

        pieceX = (10 - shape[0].length) / 2;
        pieceY = 0;

        return true;
    }

    // 블록 이동
    public boolean move(int dx, int dy) {
        if (currentPiece == null) {
            return false;
        }

        int[][] shape = currentPiece.getShape();

        int newX = pieceX + dx;
        int newY = pieceY + dy;

        // 게임판 왼쪽, 오른쪽 경계 확인
        if (newX < 0 || newX + shape[0].length > 10) {
            return false;
        }

        // 게임판 위쪽, 아래쪽 경계 확인
        if (newY < 0 || newY + shape.length > 20) {
            return false;
        }

        pieceX = newX;
        pieceY = newY;

        return true;
    }

    public int[][] getCells() {
        return cells;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public int getPieceX() {
        return pieceX;
    }

    public int getPieceY() {
        return pieceY;
    }
}
