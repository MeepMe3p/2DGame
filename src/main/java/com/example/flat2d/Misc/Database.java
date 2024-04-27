package com.example.flat2d.Misc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/*
* DATABASE CLASS WHERE WE CREATE THE DATABASE, DO THE CRUD AND MANY
* MORE GAME RELATED STUFF*/
public class Database {

    public static void initDb(){
        try(Connection conn = MySQLConnection.getConnection();
            Statement statement = conn.createStatement()){
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS tblUserAccount(\n" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password VARCHAR(100) NOT NULL" +
                    ");"

            );
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS tblUserProfile(\n" +
                            "player_id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                            "player_name TEXT UNIQUE NOT NULL," +
                            "level INTEGER CHECK (level BETWEEN 1 AND 10)," +
                            "highscore INTEGER," +
                            "exp_points INTEGER," +
                            "FOREIGN KEY(player_id) REFERENCES tblUserAccount(id)"+
                            ");"
            );

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static boolean createUser(String username, String password){
        try(Connection conn = MySQLConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO tblUserAccount (username,password) VALUES (?,?)");
            PreparedStatement statement2 = conn.prepareStatement(
                    "INSERT INTO tblUserProfile (player_name,level,high_score,exp_points) VALUES(?,?,?,?)"
            );
            statement.setString(1, username);
            statement.setString(2, password);
            statement2.setString(1,username);
            statement2.setInt(2,1);
            statement2.setInt(3,0);
            statement2.setInt(4,0);
            try {
                statement.executeUpdate();
                statement2.executeUpdate();
            } catch (SQLException e) {
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }




}
