package tetris.view;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Top extends JFrame {

    private ResultSet resultSet = null;

    private JLabel nb1 = new JLabel("");

    private static Top top;

    private Top() {

        setSize(300, 500);
        setLayout(null);
        setTitle("Main Menu");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);


        String jdbcURL = "jdbc:postgresql://localhost:5432/tetris";
        String user = "postgres";
        String password = "admin";
        Connection databaseConnection;
        try {
            databaseConnection = DriverManager.getConnection(jdbcURL, user, password);
            Statement statement = databaseConnection.createStatement();
            resultSet = statement.executeQuery("select name,score from players join games g on players.id = g.player_id order by score;");

/*            String username = "mihai";
            String pass = "postgres";
            String nickname = "mihai9909";

            PreparedStatement sqlQuery = databaseConnection.prepareStatement("insert into players (id,hashed_user,hashed_pass,name) values (1,?,?,)");

            String hashed_user = sha256(username);
            String hashed_password = sha256(pass);

            sqlQuery.setString(1,hashed_user);
            sqlQuery.setString(2,hashed_password);
            sqlQuery.setString(3,nickname);

            sqlQuery.execute();
            */

        } catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.err.println("Database connection error");
        }

        addComponents();
    }

    public static Top getInstance(){
        if(top == null)
            top = new Top();
        return top;
    }

    private void addComponents(){
        addNb1();
    }

    private void addNb1(){

        nb1.setBounds(50,0,400,70);

        try {
            resultSet.next();
            String nickname = resultSet.getString("name").trim();
            String score = resultSet.getString("score").trim();
            System.out.println(score);
            String text = "1." + nickname + " : " + score;
            System.out.println(text);
            nb1.setText(text);
        }catch (SQLException e){
            e.printStackTrace();
        }
        add(nb1);
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
