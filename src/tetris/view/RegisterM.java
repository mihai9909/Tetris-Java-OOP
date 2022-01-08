package tetris.view;

import tetris.Login;
import tetris.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PublicKey;
import java.util.EventListener;

public class RegisterM extends JFrame{
    private JTextField userTxt;
    private JPanel panel1;
    private JPasswordField passwordField;
    private JTextField nicknameField;
    private JButton registerButton;
    private JLabel nicknameExistsText;
    private JLabel userExistsText;
    private JFrame frame;
    private Model model;

    private static RegisterM registerM;

    private RegisterM(){
        frame = new JFrame("Register");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(450, 400));
        frame.setResizable(false);

        frame.add(panel1);

        registerButton.addActionListener(new RegisterButtonActionListener());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        model = Model.getInstance();
    }

    public static RegisterM getInstance(){
        if(registerM == null)
            registerM = new RegisterM();
        return registerM;
    }

    private class RegisterButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            nicknameExistsText.setVisible(model.nicknameExists(nicknameField.getText()));
            userExistsText.setVisible(model.credentialsExist(userTxt.getText(),passwordField.getText()));

            if(!model.nicknameExists(nicknameField.getText()) && !model.credentialsExist(userTxt.getText(),passwordField.getText())) {
                model.registerUserIntoDB(userTxt.getText(), passwordField.getText(), nicknameField.getText());
                frame.setVisible(false);
                Login.getInstance().setVisible(true);
            }
        }
    }
    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }
}
