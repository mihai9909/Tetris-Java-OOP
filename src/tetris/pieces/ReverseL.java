package tetris.pieces;

import tetris.GamePanel;

public class ReverseL extends Piece {
    public ReverseL(int UNIT_SIZE, int WINDOW_HEIGHT, int WINDOW_WIDTH, int leftOffset, int topOffset, GamePanel panel) {
        setRunning(true);
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.UNIT_SIZE = UNIT_SIZE;

        this.panel = panel;
        color = 255165000;
        x[0] = UNIT_SIZE * (leftOffset);
        y[0] = UNIT_SIZE * topOffset;

        y[1] = y[2] = y[3] = (topOffset + 1) * UNIT_SIZE;

        x[1] = UNIT_SIZE * (leftOffset);
        x[2] = UNIT_SIZE * (leftOffset + 1);
        x[3] = UNIT_SIZE * (leftOffset + 2);
    }
}
