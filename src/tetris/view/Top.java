package tetris.view;

import tetris.model.Model;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;

public class Top extends JFrame {

    private JButton back = new JButton("<-");
    private JTable table;
    JScrollPane scrollPane;

    private final Model model;

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
        addBackButton();
    }

    private void addTable(){
        try {
            String[] columns = {"ID","NAME","SCORE"};
            ResultSet resultSet = model.getTopPlayers();
            String[][] data = {         //this should be done in the presenter
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

    private void addBackButton(){
        back.setBounds(5,5,30,30);
        back.addActionListener(new BackButtonActionListener());
        add(back);
    }

    private class BackButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            dispose();
            MainMenu.getInstance().setVisible(true);
        }
    }
}
