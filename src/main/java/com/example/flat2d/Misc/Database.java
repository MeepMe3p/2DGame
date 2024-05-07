package com.example.flat2d.Misc;

import final_project_socket.database.MySQLConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
* DATABASE CLASS WHERE WE CREATE THE DATABASE, DO THE CRUD AND MANY
* MORE GAME RELATED STUFF*/
public class Database {
    public static boolean createUser(String username, String password){
        try(Connection conn = MySQLConnector.getConnection()) {
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
