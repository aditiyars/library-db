package com.exercise.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class Database {
    
    Dotenv dotenv = Dotenv.load();
    private final String url = "jdbc:postgresql://"+dotenv.get("HOST")+":"+dotenv.get("PORT")+"/"+dotenv.get("DATABASE");
    private final String user = dotenv.get("USER");
    private final String password = dotenv.get("PASSWORD");

    public Connection connect(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            // System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
