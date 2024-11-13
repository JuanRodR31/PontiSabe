package com.pontisabe.pontisabe.DatabaseManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// public class DatabaseConnection {

//     private static final String URL_VARIABLE = "URL";
//     private static final String USER_VARIABLE = "USER";
//     private static final String PASSWORD_VARIABLE = "PASSWORD";

//     private static final String URL = "jdbc:h2:mem:testdb";
//     private static final String USER = "pontisabe_user";
//     private static final String PASSWORD = "secret";
//     public static Connection getConnection() throws SQLException {

//         String url = System.getenv(URL_VARIABLE) != null ? System.getenv(URL_VARIABLE) : URL;
//         String user = System.getenv(USER_VARIABLE) != null ? System.getenv(USER_VARIABLE) : USER;
//         String password = System.getenv(PASSWORD_VARIABLE) != null ? System.getenv(PASSWORD_VARIABLE) : PASSWORD; 

//         System.out.println("URL:" + url);
//         System.out.println("USER:" + user);
//         System.out.println("PASSWORD:" + password);


//         return DriverManager.getConnection(url, user, password);
//     }
// }
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/pontisabe_db";
    private static final String USER = "pontisabe_user";
    private static final String PASSWORD = "secret";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
// spring.datasource.url=jdbc:h2:mem:testdb
// spring.datasource.driverClassName=org.h2.Driver
// spring.datasource.username=sa
// spring.datasource.password=password
// spring.jpa.database-platform=org.hibernate.dialect.H2Dialect