package com.pontisabe.pontisabe.Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

import com.pontisabe.pontisabe.DatabaseManagement.DatabaseConnection;
import com.pontisabe.pontisabe.Entities.User;

public class AccountService {
    //CU-001 Crear Usuario
    private final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@javeriana\\.edu\\.co$";

    public boolean validatePassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }
    public boolean validateMail(String username) {
        return username.matches(EMAIL_PATTERN);
    }
    public boolean hasEmptyFields(User user) {
        return (user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getNames() == null || user.getNames().isEmpty() ||
                user.getLastNames() == null || user.getLastNames().isEmpty());
    }
    
    public boolean addUser(User user) {
        String sql = "INSERT INTO User (username, names, lastNames, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getNames());
            pstmt.setString(3, user.getLastNames());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
            return false;
        }
    }

    public String createUser(String username, String password, String names, String lastNames, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNames(names);
        user.setLastNames(lastNames);
        user.setEmail(email);
        if (hasEmptyFields(user)) {
            return ("Hay campos vacios");
        }
        if (!validatePassword(password)) {
            return ("La contraseña debe contener una mayuscula, un numero y un caracter especial");
        }
        if (!validateMail(email)) {
            return ("Debe usar el email institucional para registrarse");
        }
        if (!addUser(user)) {
            return ("Error al insertar usuario");
        }
        return ("cuenta creada exitosamente");
    }   
}