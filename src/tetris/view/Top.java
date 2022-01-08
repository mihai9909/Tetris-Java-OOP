package tetris.view;

import tetris.model.Model;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.lang.annotation.Target;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Top extends JFrame {

    private JButton back;
    private JTable table;
    JScrollPane scrollPane;

    private Model model;

    private static Top top;

    private Top() {

        setSize(300, 500);
        setLayout(null);
        setTitle("Scoreboard");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        model = Model.getInstance();

        addComponents();

        setVisible(true);
    }

    public static Top getInstance(){
        if(top == null)
            top = new Top();
        return top;
    }

    private void addComponents(){
        addTable();
    }

    private void addTable(){

        try {
            String[] columns = {"ID","NAME","SCORE"};
            ResultSet resultSet = model.getTopPlayers();
            String[][] data = {
                    {"1",resultSet.getString("name").trim(),resultSet.getString("score").trim()},
                    {"2","fsd","100"}
            };
            table = new JTable(data,columns);
        }catch (SQLException e){
            e.printStackTrace();
        }

        table.setCellSelectionEnabled(true);
        table.setBounds(40,50,200,400);
        table.setDefaultEditor(Object.class,null);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40,50,200,400);
        add(scrollPane);
    }

    public static String sha256(final String data) {
        try {
            final byte[] hash = MessageDigest.getInstance("SHA-256").digest(data.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hashStr = new StringBuilder(hash.length);

            for (byte hashByte : hash)
                hashStr.append(Integer.toHexString(255 & hashByte));

            return hashStr.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
