package tetris.model;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class Model {
    private static Model model;
    private Connection databaseConnection;

    private ResultSet resultSet = null;

    private Model(){
        String jdbcURL = "jdbc:postgresql://localhost:5432/tetris";
        String user = "postgres";
        String password = "admin";
        try {
            databaseConnection = DriverManager.getConnection(jdbcURL, user, password);

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
    }

    public static Model getInstance(){
        if(model == null)
            model = new Model();
        return model;
    }

    public ResultSet getTopPlayers() throws SQLException {
        Statement statement = databaseConnection.createStatement();
        resultSet = statement.executeQuery("select name,score from players join games g on players.id = g.player_id order by score;");
        resultSet.next();
        return resultSet;
    }

    public static Font getTetrisFont(){
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
}
