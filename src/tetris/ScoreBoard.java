/*
* UI for the score board
* Also handles when the timer should speed up as the level increases
*
* */

package tetris;

import tetris.pieces.L;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScoreBoard {
    private int SCORE;
    private int Level;
    private int LINES;
    private int BOARD_WIDTH;
    private int SCORE_BOARD_WIDTH;
    private int WINDOW_HEIGHT;

    private boolean visibleGameOver;

    private JLabel score = new JLabel("",SwingConstants.CENTER);
    private JLabel scoreText = new JLabel("Score",SwingConstants.CENTER);
    private JLabel lines = new JLabel("",SwingConstants.CENTER);
    private JLabel linesText = new JLabel("Lines",SwingConstants.CENTER);
    private JLabel gameOverText = new JLabel("<html><font color='red'>GAME OVER</font></html>",SwingConstants.CENTER);

    private GamePanel panel;

    public ScoreBoard(int BOARD_WIDTH,int WINDOW_HEIGHT,int SCORE_BOARD_WIDTH, GamePanel panel) {
        this.panel = panel;
        SCORE = 0;
        LINES = 0;
        Level = 0;
        this.BOARD_WIDTH = BOARD_WIDTH;
        this.SCORE_BOARD_WIDTH = SCORE_BOARD_WIDTH;
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
    }

    public JLabel getScoreText() {
        return scoreText;
    }

    public JLabel getScore() {
        return score;
    }

    public JLabel getGameOverText() {
        return gameOverText;
    }

    public JLabel getLines(){
        return lines;
    }

    public JLabel getLinesText(){
        return linesText;
    }

    public int getSCORE() {
        return SCORE;
    }

    public int getLINES() {
        return LINES;
    }


    public void setVisibleGameOver(boolean visibleGameOver) {
        this.visibleGameOver = visibleGameOver;
    }

    public void setGameOverText(boolean visible){
        gameOverText.setBounds(BOARD_WIDTH/2-150,WINDOW_HEIGHT/2-150,300,300);
        gameOverText.setFont(new Font("MONOSPACED",Font.BOLD,40));
        gameOverText.setVisible(visible);
    }

    public void setScore(){
        score.setBounds(BOARD_WIDTH+SCORE_BOARD_WIDTH/2-75,scoreText.getY()+30,150,150);
        score.setText(String.valueOf(SCORE));
        score.setFont(new Font("MONOSPACED",Font.BOLD,30));
    }

    public void setLinesText(){
        linesText.setBounds(BOARD_WIDTH+SCORE_BOARD_WIDTH/2-75,score.getY()+50,150,150);
        linesText.setFont(new Font("MONOSPACED",Font.BOLD,30));
    }

    public void setScoreText(){
        scoreText.setBounds(BOARD_WIDTH+SCORE_BOARD_WIDTH/2-75,WINDOW_HEIGHT/50,150,150);
        scoreText.setFont(new Font("MONOSPACED",Font.BOLD,30));
    }

    public void setLines(){
        lines.setBounds(BOARD_WIDTH+SCORE_BOARD_WIDTH/2-75,linesText.getY()+30,150,150);
        lines.setText(String.valueOf(LINES));
        lines.setFont(new Font("MONOSPACED",Font.BOLD,30));
    }

    public void drawBoard(Graphics g){
        g.setColor(Color.pink);
        g.fillRect(BOARD_WIDTH,0,SCORE_BOARD_WIDTH,WINDOW_HEIGHT);
        setGameOverText(visibleGameOver);
        setScoreText();
        setScore();
        setLinesText();
        setLines();
    }

    public void incrementScore(int linesCleared){
        switch (linesCleared){
            case 1:
                SCORE += 40;
                break;
            case 2:
                SCORE += 100;
                break;
            case 3:
                SCORE += 300;
                break;
            case 4:
                SCORE += 1200;
                break;
            default:
                break;
        }
    }

    public void incrementLines(int linesCleared){
        LINES += linesCleared;
        if(LINES >= (Level+1)*10){      //increment level every 10 cleared lines
            Level++;
            panel.halfDelay();
        }
    }
}
