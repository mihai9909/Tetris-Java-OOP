package tetris;
import tetris.view.MainMenu;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Tetris {
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

    public static void main(String[] args){

        String jdbcURL = "jdbc:postgresql://localhost:5432/tetris";
        String user = "postgres";
        String password = "admin";
        try {
            Connection databaseConnection = DriverManager.getConnection(jdbcURL, user, password);

//            String username = "mihai";
//            String pass = "postgres";
//            String nickname = "mihai9909";
//
//            PreparedStatement sqlQuery = databaseConnection.prepareStatement("insert into players (id,hashed_user,hashed_pass,name) values (1,?,?,)");
//
//            String hashed_user = sha256(username);
//            String hashed_password = sha256(pass);
//
//            sqlQuery.setString(1,hashed_user);
//            sqlQuery.setString(2,hashed_password);
//            sqlQuery.setString(3,nickname);
//
//            sqlQuery.execute();

        } catch (SQLException e){

            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.err.println("Database connection error");
        }

        MainMenu.getInstance();
    }
}
