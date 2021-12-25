package tetris.pieces;

import tetris.GamePanel;

public class Squiggly extends Piece {
    public Squiggly(int UNIT_SIZE, int WINDOW_HEIGHT, int WINDOW_WIDTH, int leftOffset, int topOffset, GamePanel panel) {
        setRunning(true);
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.UNIT_SIZE = UNIT_SIZE;
        this.panel = panel;

        color = 127255000;
        y[0] = y[1] = UNIT_SIZE * topOffset;
        y[2] = y[3] = (topOffset + 1) * UNIT_SIZE;
        x[0] = leftOffset * UNIT_SIZE;
        x[1] = x[2] = (leftOffset + 1) * UNIT_SIZE;
        x[3] = (leftOffset + 2) * UNIT_SIZE;
    }
}
