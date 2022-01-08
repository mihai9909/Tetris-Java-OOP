package tetris;

import com.sun.tools.javac.Main;
import tetris.model.Model;
import tetris.view.MainMenu;
import tetris.view.RegisterM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login{
    private JTextField UserTxt;
    private JPanel panel1;
    private JPasswordField password;
    private JButton logInButton;
    private JButton registerButton;
    private JLabel checkCredentialsText;
    private JFrame frame;
    private Model model;

    private static Login login;

    private Login(){
        model = Model.getInstance();

        frame = new JFrame("Log in");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(450, 400));
        frame.setResizable(false);

        frame.add(panel1);
        logInButton.addActionListener(new LoginButtonListener());
        registerButton.addActionListener(new RegisterButtonListener());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static Login getInstance(){
        if(login == null){
            login = new Login();
        }
        return login;
    }

    private class LoginButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            checkCredentialsText.setVisible(false);
            if(model.credentialsExist(UserTxt.getText(),password.getText())){
                frame.dispose();
                MainMenu.getInstance().setVisible(true);
            }else{
                checkCredentialsText.setVisible(true);
            }
        }
    }

    private class RegisterButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            frame.setVisible(false);
            RegisterM.getInstance().setVisible(true);
        }
    }

    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }
}
