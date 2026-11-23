package tetris.game;

public class Piece {

    // 테트리스 블록 7종의 초기 모양
    private static final int[][][] SHAPES = {
        {{1, 1, 1, 1}},             // I
        {{1, 1}, {1, 1}},           // O
        {{0, 1, 0}, {1, 1, 1}},     // T
        {{0, 1, 1}, {1, 1, 0}},     // S
        {{1, 1, 0}, {0, 1, 1}},     // Z
        {{1, 0, 0}, {1, 1, 1}},     // J
        {{0, 0, 1}, {1, 1, 1}}      // L
    };

    private final int type;
    private final int[][] shape;

    public Piece(int type) {
        if (type < 1 || type > 7) {
            throw new IllegalArgumentException("블록 종류는 1~7이어야 합니다.");
        }

        this.type = type;
        this.shape = SHAPES[type - 1];
    }

    public int getType() {
        return type;
    }

    public int[][] getShape() {
        return shape;
    }
}
