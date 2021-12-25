package tetris.pieces;
import tetris.GamePanel;

import java.awt.*;

abstract public class Piece {

    protected int UNIT_SIZE;
    protected int WINDOW_HEIGHT;
    protected int WINDOW_WIDTH;
    protected static int[] x = new int[4];
    protected static int[] y = new int[4];
    private boolean running = false;
    protected int color;

    public int getX(int i){
        return x[i];
    }
    public int getY(int i){
        return y[i];
    }
    public int getColor(){
        return color;
    }

    public boolean getRunning(){
        return running;
    }
    public void setRunning(boolean running){
        this.running = running;
    }


    GamePanel panel;

    public void rotatePiece(){
        for(int i = 0;i < 4;i++){
            if(x[i]- x[2] + y[2] < WINDOW_HEIGHT) {
                int aux = x[i];
                x[i] = y[2] - y[i] + x[2];
                y[i] = aux - x[2] + y[2];
            }else{
                while(x[i]- x[2] + y[2] >= WINDOW_HEIGHT)   //move piece 1 unit up
                    for(int j = 0;j < 4;j++)
                        y[j] -= UNIT_SIZE;
                int aux = x[i];
                x[i] = y[2] - y[i] + x[2];
                y[i] = aux - x[2] + y[2];
            }
        }
    }

    public void moveDown(){
        for (int i = 0; i < 4; i++) {
            y[i] += UNIT_SIZE;
        }

        if(!checkCollisions('D')) {
            for(int j = 0; j < 4;j++)   //object collides so we have to move it 1 tile up
                y[j] -= UNIT_SIZE;
            panel.pieceArrangement.placePiece(this);
            this.running = false;
            panel.newPiece();
        }
    }

    public void placePiece(){
        while(checkCollisions('D'))
            for (int i = 0; i < 4; i++)
                y[i] += UNIT_SIZE;

        for(int j = 0; j < 4;j++)   //object collides so we have to move it 1 tile up
            y[j] -= UNIT_SIZE;

        panel.pieceArrangement.placePiece(this);
        this.running = false;
        panel.newPiece();
    }

    public void moveLRD(char dir) {
        if(this.running) {
            switch (dir) {
                case 'L':
                    if(checkCollisions(dir))
                        for (int i = 0; i < 4; i++)
                            x[i] -= UNIT_SIZE;
                    break;
                case 'R':
                    if(checkCollisions(dir))
                        for (int i = 0; i < 4; i++)
                            x[i] += UNIT_SIZE;
                    break;
            }
        }
    }

    public boolean checkCollisions(char dir){       //return false when collides / true when it doesnt

        if(dir == 'L')
        {
            for(int i = 0;i<4;i++) {
                int yPos = y[i]/UNIT_SIZE;
                int xPos = x[i]/UNIT_SIZE;

                if(xPos <= 0 || panel.getCoveredTiles(yPos,xPos-1) != 0) {
                    return false;
                }
            }
        }
        else if(dir == 'D'){
            for (int i = 0; i < 4; i++) {
                int yPos = y[i] / UNIT_SIZE;
                int xPos = x[i] / UNIT_SIZE;

                if (yPos >= panel.pieceArrangement.getNbRows() || panel.getCoveredTiles(yPos,xPos) != 0) {
                    return false;
                }
            }
        }
        else if(dir == 'R') {
            for (int i = 0; i < 4; i++) {
                int yPos = y[i] / UNIT_SIZE;
                int xPos = x[i] / UNIT_SIZE;
                if ((xPos > panel.pieceArrangement.getNbColumns()-2 || panel.getCoveredTiles(yPos,xPos+1) != 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void handleRotationCol(){
        for(int i = 0;i < 4;i++){
            int xPos = x[i]/UNIT_SIZE;
            int yPos = y[i]/UNIT_SIZE;

            while(xPos >= panel.pieceArrangement.getNbColumns()){
                for(int j = 0;j < 4;j++)
                    x[j] -= UNIT_SIZE;
                xPos--;
            }
            while(xPos < 0) {
                for (int j = 0; j < 4; j++)
                    x[j] += UNIT_SIZE;
                xPos++;
            }
            while(panel.getCoveredTiles(yPos,xPos) != 0){
                for(int j = 0;j < 4;j++) {
                    y[j] -= UNIT_SIZE;
                    if(y[j] < 0){   //fixes a bug where you rotate out of the upper bound of the coveredTiles matrix
                        panel.gameOver();
                        return;
                    }
                }
                yPos--;
            }
        }
    }

    public void drawPiece(Graphics g){
        int blue = color % 256;
        int green = (color / 256) % 256;
        int red = ((color / 256) / 256) % 256;

        for(int i = 0;i<4;i++) {
            g.setColor(new Color(red,green,blue));
            g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            g.setColor(Color.black);
            g.drawRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);    //outline of the piece
        }
    }
}
