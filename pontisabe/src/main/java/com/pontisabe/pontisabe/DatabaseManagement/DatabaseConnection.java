package com.pontisabe.pontisabe.DatabaseManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://bdpontisabeok.mysql.database.azure.com:3306/pontisabedb";
    private static final String USER = "saponitsaberoot";
    private static final String PASSWORD = "Juanloklo2004.";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


// public class DatabaseConnection {
//     private static final String URL = "jdbc:mysql://localhost:3306/pontisabe_db";
//     private static final String USER = "pontisabe_user";
//     private static final String PASSWORD = "secret";

//     public static Connection getConnection() throws SQLException {
//         return DriverManager.getConnection(URL, USER, PASSWORD);
//     }
// }