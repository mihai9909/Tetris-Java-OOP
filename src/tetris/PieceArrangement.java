/*
* Handles placed pieces using a matrix coveredTiles
* Also handles lines that need to be deleted and increments the score/lines on the scoreboard
* Deletion and checking can be done more efficiently
* */

package tetris;

import tetris.pieces.Piece;

import java.awt.*;

public class PieceArrangement {

    public int getCoveredTiles(int y,int x) {
        return coveredTiles[y][x];
    }

    private int[][] coveredTiles;

    private int nbRows, nbColumns;

    public int getNbRows() {
        return nbRows;
    }

    public void setNbRows(int nbRows) {
        this.nbRows = nbRows;
    }

    public int getNbColumns() {
        return nbColumns;
    }

    public void setNbColumns(int nbColumns) {
        this.nbColumns = nbColumns;
    }

    private final int UNIT_SIZE;
    private ScoreBoard scoreBoard;

    public PieceArrangement(int UNIT_SIZE,int WINDOW_HEIGHT,int WINDOW_WIDTH, ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;

        this.UNIT_SIZE = UNIT_SIZE;

        nbRows = WINDOW_HEIGHT/UNIT_SIZE;
        nbColumns = WINDOW_WIDTH/UNIT_SIZE;

        coveredTiles = new int[nbRows][];
        for(int i = 0;i < nbRows;i++) {
            coveredTiles[i] = new int[nbColumns];
        }
    }

    public void placePiece(Piece piece) {
        for(int i = 0;i < 4;i++) {
            int column = piece.getX(i)/UNIT_SIZE ,row = piece.getY(i)/UNIT_SIZE;
                coveredTiles[row][column] = piece.getColor();
        }
        deleteLines();
    }

    public int checkLines(){    //returns the row which needs to be cleared
        for(int i=0;i < nbRows;i++) {
            int ok = 1;
            for (int j = 0; j < nbColumns; j++) {
                if (coveredTiles[i][j] == 0) {
                    ok = 0;
                    break;
                }
            }
            if(ok == 1)
                return i;
        }
        return -1;
    }

    public void deleteLines() {
        int clearedLines = 0;
        while (checkLines() != -1) {
            int row = checkLines();

            for (int j = row; j > 0; j--) {
                for (int k = 0; k < nbColumns; k++)
                    coveredTiles[j][k] = coveredTiles[j - 1][k];
            }
            clearedLines++;
        }
        scoreBoard.incrementScore(clearedLines);
        scoreBoard.incrementLines(clearedLines);
    }

    public void printCoverageMatrix() {
        for(int i = 0;i < nbRows;i++) {
            for (int j = 0; j < nbColumns; j++) {
                System.out.print(coveredTiles[i][j]);
                System.out.print(' ');
            }
            System.out.print('\n');
        }
        System.out.println('\n');
    }

    public void drawArrangement(Graphics g){
        for(int i = 0;i < this.nbRows;i++){
            for(int j = 0;j < this.nbColumns;j++) {
                if(this.coveredTiles[i][j] != 0){
                    int blue = coveredTiles[i][j] % 256;
                    int green = (coveredTiles[i][j] / 256) % 256;
                    int red = ((coveredTiles[i][j] / 256) / 256) % 256;
                    g.setColor(new Color(red,green,blue));
                    g.fillRect(j*UNIT_SIZE,i*UNIT_SIZE,UNIT_SIZE,UNIT_SIZE);
                    g.setColor(Color.black);
                    g.drawRect(j*UNIT_SIZE,i*UNIT_SIZE,UNIT_SIZE,UNIT_SIZE); //draw outline
                }
            }
        }
    }
}
