package tetris;

import tetris.pieces.*;
import tetris.view.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private final int BOARD_WIDTH = 250;
    private final int UI_WIDTH = 150;
    private final int WINDOW_HEIGHT = 500;
    private final int UNIT_SIZE = 25;
    private int DELAY = 800;

    public Piece piece;
    public PieceArrangement pieceArrangement;
    public ScoreBoard scoreBoard;

    Timer timer;
    Random random;

    GameFrame gameFrame;

    public GamePanel(GameFrame gameFrame){
        this.gameFrame = gameFrame;

        random = new Random();

        scoreBoard = new ScoreBoard(BOARD_WIDTH,WINDOW_HEIGHT,UI_WIDTH,this);
        pieceArrangement = new PieceArrangement(UNIT_SIZE, WINDOW_HEIGHT, BOARD_WIDTH, scoreBoard);
        newPiece();

        addComponents();

        this.setPreferredSize(new Dimension(BOARD_WIDTH+UI_WIDTH,WINDOW_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.startGame();
    }

    public void startGame(){
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        scoreBoard.drawBoard(g);
        piece.drawPiece(g);
        pieceArrangement.drawArrangement(g);

        add(scoreBoard.getScore());
    }

    void drawGridLines(Graphics g){
        for(int i = 0;i<WINDOW_HEIGHT/UNIT_SIZE;i++) {          //gridlines (optional)
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,WINDOW_HEIGHT);
            g.drawLine(0,i*UNIT_SIZE, BOARD_WIDTH,i*UNIT_SIZE);
        }
    }   //optional

    public void newPiece(){
        int randPiece = random.nextInt(7);

        switch (randPiece) {
            case 0 -> piece = new L(UNIT_SIZE, WINDOW_HEIGHT, BOARD_WIDTH, 3, 2, this);
            case 1 -> piece = new Line(UNIT_SIZE, WINDOW_HEIGHT, BOARD_WIDTH, 3, 2, this);
            case 2 -> piece = new ReverseL(UNIT_SIZE, WINDOW_HEIGHT, BOARD_WIDTH, 3, 2, this);
            case 3 -> piece = new Squiggly(UNIT_SIZE, WINDOW_HEIGHT, BOARD_WIDTH, 3, 2, this);
            case 4 -> piece = new ReverseSquiggly(UNIT_SIZE, WINDOW_HEIGHT, BOARD_WIDTH, 3, 2, this);
            case 5 -> piece = new T(UNIT_SIZE, WINDOW_HEIGHT, BOARD_WIDTH, 3, 2, this);
            case 6 -> piece = new Square(UNIT_SIZE, WINDOW_HEIGHT, BOARD_WIDTH, 3, 2, this);
        }
        if(!piece.checkCollisions('D')){
            gameOver();
        }
    }

    public void gameOver(){
        piece.setRunning(false);
        scoreBoard.setVisibleGameOver(true);
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent key){
            if(!piece.getRunning()) {
                if(key.getKeyCode() == KeyEvent.VK_ENTER) {
                    MainMenu.getInstance().setVisible(true); //TODO: save game info into database
                    gameFrame.dispose();
                }
                return;
            }

            switch (key.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    piece.moveLRD('L');
                    repaint();
                    break;
                case KeyEvent.VK_RIGHT:
                    piece.moveLRD('R');
                    repaint();
                    break;
                case KeyEvent.VK_DOWN:
                    piece.moveDown();
                    repaint();
                    break;
                case KeyEvent.VK_SPACE:
                    piece.placePiece();
                    repaint();
                    break;
                case KeyEvent.VK_Z:
                    piece.rotatePiece();
                    piece.handleRotationCol();
                    repaint();
                    break;
            }
        }
    }

    public int getCoveredTiles(int y, int x) {
        return pieceArrangement.getCoveredTiles(y,x);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(piece.getRunning()) {
            piece.moveDown();
        }
        repaint();
    }

    private void addComponents(){   //add UI
        add(scoreBoard.getScoreText());
        add(scoreBoard.getGameOverText());
        add(scoreBoard.getLinesText());
        add(scoreBoard.getLines());
    }

    public void halfDelay(){    //makes a new timer that runs twice as fast
        DELAY /= 2;
        timer.setDelay(DELAY);
    }
}
