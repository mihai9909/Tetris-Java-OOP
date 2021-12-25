package tetris.pieces;

import tetris.GamePanel;

public class T extends Piece {
    public T(int UNIT_SIZE, int WINDOW_HEIGHT, int WINDOW_WIDTH, int leftOffset, int topOffset, GamePanel panel) {
        setRunning(true);
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.UNIT_SIZE = UNIT_SIZE;

        this.panel = panel;

        color = 255000255;
        y[0] = y[1] = y[2] = topOffset * UNIT_SIZE;
        y[3] = (topOffset + 1) * UNIT_SIZE;
        x[0] = leftOffset * UNIT_SIZE;
        x[3] = x[2] = (leftOffset + 1) * UNIT_SIZE;
        x[1] = (leftOffset + 2) * UNIT_SIZE;
    }
}