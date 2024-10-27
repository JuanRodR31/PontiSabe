package com.pontisabe.pontisabe.Services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import java.sql.Connection;

import com.pontisabe.pontisabe.DatabaseManagement.DatabaseConnection;
import com.pontisabe.pontisabe.Entities.User;

@Service
public class AccountService {
    //CU-001 Crear Usuario
    private final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[.#?!@$ %^&*-]).{8,}$";
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
    
    public boolean createUser(User user) {
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
        if (!createUser(user)) {
            return ("Error al insertar usuario");
        }
        return ("cuenta creada exitosamente");
    }
    
    //CU-002 Login
    public boolean login (String username, String password) {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Usuario logueado");
                    return true;
                }
            }
        }catch (SQLException e) {
            System.out.println("Error al loguear: " + e.getMessage());
        }
        return false;
    }
    //Buscar usuario por id
    public User findUserById(Long userId) {
        String sql = "SELECT * FROM User WHERE id = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setNames(rs.getString("names"));
                    user.setLastNames(rs.getString("lastNames"));
                    user.setEmail(rs.getString("email"));
                    // Establecer otros atributos de 'User' si es necesario
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
        }

        return user;
    }
}
