package tetris.model;

import tetris.view.RegisterM;

import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Model {
    private static Model model;
    private Connection databaseConnection;

    private Model(){
        String jdbcURL = "jdbc:postgresql://localhost:5432/tetris";
        String user = "postgres";
        String password = "admin";
        try {
            databaseConnection = DriverManager.getConnection(jdbcURL, user, password);

        } catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.err.println("Database connection error");
        }
    }

    public static Model getInstance(){
        if(model == null)
            model = new Model();
        return model;
    }

    public ResultSet getTopPlayers() throws SQLException {
        ResultSet resultSet = null;
        Statement statement = databaseConnection.createStatement();
        resultSet = statement.executeQuery("select name,score from players join games g on players.id = g.player_id order by score;");
        resultSet.next();
        return resultSet;
    }

    public Font getTetrisFont(){
        Font font = null;
        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream("dependencies/futurao.ttf"));
            font = Font.createFont(Font.TRUETYPE_FONT, myStream);

            font = font.deriveFont(45f);
        } catch (IOException |FontFormatException e) {
            e.printStackTrace();
        }
        return font;
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

    public void registerUserIntoDB(String username, String pass, String nickname){
        try {
            Statement nbUsersStatement = databaseConnection.createStatement();
            ResultSet resultNbUsers = nbUsersStatement.executeQuery("select count(id) from players;");
            resultNbUsers.next();
            int nbUsers = resultNbUsers.getInt(1) + 1;

            PreparedStatement sqlQuery = databaseConnection.prepareStatement("insert into players (id,hashed_user,hashed_pass,name) values (?,?,?,?);");

            String hashed_user = sha256(username);
            String hashed_password = sha256(pass);

            sqlQuery.setInt(1,nbUsers);
            sqlQuery.setString(2, hashed_user);
            sqlQuery.setString(3, hashed_password);
            sqlQuery.setString(4, nickname);

            sqlQuery.execute();
        }catch (SQLException e){
            System.err.println("Register Error");
            e.printStackTrace();
        }
    }

    public boolean nicknameExists(String nickname){
        try {
            PreparedStatement sqlQuery = databaseConnection.prepareStatement("select name from players where name = ?;",ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_UPDATABLE);
            sqlQuery.setString(1,nickname);

            ResultSet result = sqlQuery.executeQuery();
            return result.first();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean credentialsExist(String username, String pass){
        try {
            PreparedStatement sqlQuery = databaseConnection.prepareStatement("select from players where hashed_user = ? and hashed_pass = ?;",ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_UPDATABLE);

            String hashed_user = sha256(username);
            String hashed_password = sha256(pass);

            sqlQuery.setString(1, hashed_user);
            sqlQuery.setString(2, hashed_password);

            ResultSet result = sqlQuery.executeQuery();
            return result.first();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public String  getNickname(String username, String pass){
        try {
            PreparedStatement sqlQuery = databaseConnection.prepareStatement("select name from players where hashed_user = ? and hashed_pass = ?;",ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_UPDATABLE);

            String hashed_user = sha256(username);
            String hashed_password = sha256(pass);

            sqlQuery.setString(1, hashed_user);
            sqlQuery.setString(2, hashed_password);

            ResultSet result = sqlQuery.executeQuery();
            result.next();
            return result.getString(1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public void insertNewGameIntoDB(int score,int lines, String name){
        try {
            PreparedStatement sqlQuery = databaseConnection.prepareStatement("select count(game_id) from games");
            ResultSet resultSet = sqlQuery.executeQuery();
            resultSet.next();
            int games = resultSet.getInt(1) + 1;

            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

            int category_id = 1;

            sqlQuery = databaseConnection.prepareStatement("select id from players where name = ?;");
            sqlQuery.setString(1,name);
            resultSet = sqlQuery.executeQuery();
            resultSet.next();
            int player_id = resultSet.getInt(1);


            sqlQuery = databaseConnection.prepareStatement("insert into games (game_id, score, line, date, category_id, player_id) VALUES (?,?,?,?,?,?);");
            sqlQuery.setInt(1,games);
            sqlQuery.setInt(2,score);
            sqlQuery.setInt(3,lines);
            sqlQuery.setDate(4,date);
            sqlQuery.setInt(5,category_id);
            sqlQuery.setInt(6,player_id);

            sqlQuery.execute();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
