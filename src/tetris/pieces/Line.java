package tetris.pieces;

import tetris.GamePanel;

public class Line extends Piece {

    public Line(int UNIT_SIZE, int WINDOW_HEIGHT, int WINDOW_WIDTH, int leftOffset, int topOffset, GamePanel panel) {
        setRunning(true);
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.WINDOW_WIDTH = WINDOW_WIDTH;
        this.UNIT_SIZE = UNIT_SIZE;

        this.panel = panel;

        color = 255255;
        y[0] = y[1] = y[2] = y[3] = UNIT_SIZE * topOffset;
        x[0] = UNIT_SIZE * (leftOffset);
        x[1] = UNIT_SIZE * (leftOffset + 1);
        x[2] = UNIT_SIZE * (leftOffset + 2);
        x[3] = UNIT_SIZE * (leftOffset + 3);
    }
}
