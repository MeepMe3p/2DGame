package com.example.flat2d.DesignPatterns;

public class User {
    private static User instance = null;
    private int userId;
    public String username;

    public User(){
        this.userId = 0;
        this.username = "if 0 id and kani nagpakita it didnt work";
    }
    public static User getInstance(){
        if(instance == null){
            return new User();
        }else{
            return instance;
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

    }
}
