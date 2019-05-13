package Main;

import Entity.Player;

import java.sql.*;

public class DataBaseManager {

    private static Connection connection;
    private static Statement statement;
    Player player;

    public DataBaseManager(Player player){

        this.player = player;
        connection = null;
        statement = null;

    }

    public void createTable() {

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:the_greedy.db");
            statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS player_info" +
                    "(Name VARCHAR(20) NOT NULL, " +
                    "health INT NOT NULL, " +
                    "kills INT NOT NULL, " +
                    "pos_x INT NOT NULL, " +
                    "pos_y INT NOT NULL);";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Table created successfully");
    }

    public void insetInTable(){

        String sql = "INSERT INTO player_info (Name, health, kills, pos_x, pos_y) " +
                "VALUES ('Lupu', " + player.getHealth() + ", " + 69 + ", " + player.getx() + ", " + player.gety() + ");";
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:the_greedy.db");
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(sql);

            statement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Records created successfully");
    }

    public void updatePlayerFromDataBase() {

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:the_greedy.db");
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM player_info;");

            while (rs.next()) {
                player.setHealth(rs.getInt("health"));
                player.setPosition(rs.getInt("pos_x"), rs.getInt("pos_y"));
                System.out.println("health in database: " + rs.getInt("health"));
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
    }

}
