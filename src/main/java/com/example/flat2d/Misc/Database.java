package com.example.flat2d.Misc;

import final_project_socket.database.MySQLConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;

/*
* DATABASE CLASS WHERE WE CREATE THE DATABASE, DO THE CRUD AND MANY
* MORE GAME RELATED STUFF*/
public class Database {
    public static void main(String[] args) {
        for(String i : getAllUsersKillsScore()){
            System.out.println(i);
        }
    }
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
    public static void insertOrUpdateUserHighScore(String username, int killScore) {
        try (Connection conn = MySQLConnector.getConnection()) {
            // Check if the user already exists in the database
            String selectQuery = "SELECT * FROM `dboop_capstone`.`tbluserhighscore` WHERE username = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, username);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                if(killScore > resultSet.getInt("killScore")){
                    String updateQuery = "UPDATE `dboop_capstone`.`tbluserhighscore` SET killScore = ? WHERE username = ?";
                    PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
                    updateStatement.setInt(1, killScore);
                    updateStatement.setString(2, username);
                    updateStatement.executeUpdate();
                    updateStatement.close();
                    System.out.println("User high score updated successfully.");
                } else {
                    System.out.println("Wala Kay Uyab! HAHAHAHAHAHA!");
                }
            } else {
                // User does not exist, insert new record
                String insertQuery = "INSERT INTO `dboop_capstone`.`tbluserhighscore` (username, killScore) VALUES (?, ?)";
                PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
                insertStatement.setString(1, username);
                insertStatement.setInt(2, killScore);
                insertStatement.executeUpdate();
                insertStatement.close();
                System.out.println("New user high score inserted successfully.");
            }
            resultSet.close();
            selectStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Database operation failed", e);
        }
    }

    public static ArrayList<String> getAllUsersKillsScore() {
        ArrayList<String> userKillsData = new ArrayList<>();
        try (Connection conn = MySQLConnector.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `dboop_capstone`.`tbluserhighscore`");
            ResultSet resultSet = preparedStatement.executeQuery();
            TreeMap<Integer, String> sortedUserData = new TreeMap<>((k1, k2) -> Integer.compare(k2, k1));
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int kills = resultSet.getInt("killScore");
                sortedUserData.put(kills, username + " : " + kills);
            }
            for (String userData : sortedUserData.values()) {
                userKillsData.add(userData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userKillsData;
    }
}
