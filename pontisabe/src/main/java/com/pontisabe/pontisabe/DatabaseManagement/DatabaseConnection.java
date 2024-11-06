package com.pontisabe.pontisabe.DatabaseManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:h2:mem:testdb";
    private static final String USER = "pontisabe_user";
    private static final String PASSWORD = "secret";

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
// spring.datasource.url=jdbc:h2:mem:testdb
// spring.datasource.driverClassName=org.h2.Driver
// spring.datasource.username=sa
// spring.datasource.password=password
// spring.jpa.database-platform=org.hibernate.dialect.H2Dialect