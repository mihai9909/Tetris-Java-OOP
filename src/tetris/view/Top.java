package tetris.view;

import tetris.model.Model;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

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
            String[] columns = new String[3];
            ArrayList<String> col = new ArrayList<>();
            col.add("ID");
            col.add("NAME");
            col.add("SCORE");
            int len = col.size();
            IntStream.range(0,len).forEach((index)-> columns[index] = col.get(index));   // used arrayList and lambda expression

            int nbGames = Model.getInstance().getNbGames();

            ResultSet resultSet = model.getTopPlayers();
            String[][] data = new String[nbGames][3];
            for(int i = 0; i < nbGames; i++){
                data[i][0] = i + 1 + "";
                data[i][1] = resultSet.getString("name").trim();
                data[i][2] = resultSet.getString("score").trim();
                resultSet.next();
            }

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
            top = null;
            MainMenu.getInstance().setVisible(true);
        }
    }
}
