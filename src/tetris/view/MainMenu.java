package tetris.view;

import tetris.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class MainMenu extends JFrame implements ActionListener {

    private final JButton startGame = new JButton("Start Game");
    private final JButton scoreboardBtn = new JButton("Scoreboard");
    private final JButton quit = new JButton("Quit");
    private final JLabel tetris = new JLabel("Tetris");
    private final JLabel pressAnyKeyText = new JLabel("Press any key to start");

    Timer timer;

    private static MainMenu obj = null;
    private MainMenu() {
        timer = new Timer(500,this);
        timer.start();

        setSize(300, 500);
        setLayout(null);
        setTitle("Main Menu");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        addKeyListener(new PressAnyBtnAdapter());
        addComponents();
    }
    public static MainMenu getInstance(){
        if(obj == null) {
            obj = new MainMenu();
        }
        return obj;
    }

    public void addComponents(){
        addTetrisLabel();
        addStartGameButton();
        addScoreboardButton();
        addQuitButton();
        addText();
    }

    public void addTetrisLabel(){
        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream("dependencies/futurao.ttf"));
            Font font = Font.createFont(Font.TRUETYPE_FONT, myStream);


            font = font.deriveFont(45f);
            tetris.setFont(font);
        } catch (IOException|FontFormatException e) {
            e.printStackTrace();
        }
        tetris.setBounds(35,0,500,150);
        tetris.setVisible(true);
        add(tetris);
    }

    public void addText(){
        pressAnyKeyText.setBounds(80,200,500,150);
        pressAnyKeyText.setVisible(true);
        add(pressAnyKeyText);
    }

    public class PressAnyBtnAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent key){
            startGame.setVisible(true);
            scoreboardBtn.setVisible(true);
            quit.setVisible(true);

            pressAnyKeyText.setText("");
        }
    }

    private void addStartGameButton(){
        startGame.setBounds(70,150,150,50);
        startGame.setVisible(false);
        startGame.addActionListener(new StartGameButtonListener());
        add(startGame);
    }

    private class StartGameButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            dispose();
            new GameFrame();
        }
    }

    private void addScoreboardButton() {
        scoreboardBtn.setBounds(70, 220, 150, 50);
        scoreboardBtn.setVisible(false);
        scoreboardBtn.addActionListener(new scoreboardButtonListener());
        add(scoreboardBtn);
    }

    private class  scoreboardButtonListener implements ActionListener{
        @Override
        public  void actionPerformed(ActionEvent e){
            setVisible(false);
            Top.getInstance();
        }
    }


    private void addQuitButton(){
        quit.setBounds(70,290,150,50);
        quit.setVisible(false);
        quit.addActionListener(new quitButtonListener());
        add(quit);
    }

    private class quitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        pressAnyKeyText.setVisible(!pressAnyKeyText.isVisible());
    }
}
