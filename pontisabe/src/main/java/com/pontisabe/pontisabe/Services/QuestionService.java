package com.pontisabe.pontisabe.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pontisabe.pontisabe.DatabaseManagement.DatabaseConnection;
import com.pontisabe.pontisabe.Entities.Forum;
import com.pontisabe.pontisabe.Entities.Question;
import com.pontisabe.pontisabe.Entities.User;

public class QuestionService {
    //CU-01 Crear Pregunta
    
    public Forum getForumByIduestionId;

    public Long insertQuestionToDbAndGetId(Question question) {
        String sql = "INSERT INTO Question (questionText, publishDate, user_id, anonym) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, question.getQuestionText());
            pstmt.setDate(2, question.getPublishDate());
            pstmt.setLong(3, question.getUser().getId());
            pstmt.setBoolean(4, question.isAnonym());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 1) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1); // Retorna el ID generado
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error inserting question: " + e.getMessage());
        }
        return null; // Retorna null si no se pudo insertar
    }
    
    
    //Buscar pregunta por id
    public Question getQuestionById(Long id) {
    String sql = "SELECT * FROM Question WHERE id = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Question question = new Question();
                    question.setId(rs.getLong("id"));
                    question.setQuestionText(rs.getString("questionText"));
                    boolean isAnonym = rs.getBoolean("anonym"); 
                    question.setAnonym(isAnonym);
                    question.setUser(findUserById(rs.getLong("user_id")));
                    if (isAnonym) {
                        question.getUser().setUsername("Anonym");
                    }
                    question.setPublishDate(rs.getDate("publishDate"));
                    // Asignar otros atributos según tu estructura de la clase Question
                    return question;
                }
            }
            } catch (SQLException e) {
                System.out.println("Error fetching question: " + e.getMessage());
            }
        return null;
    }

    //Buscar usuario por codigo
    public User findUserById(Long id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setNames(rs.getString("names"));
                    user.setLastNames(rs.getString("lastNames"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }
            } catch (SQLException e) {
                System.out.println("Error fetching user: " + e.getMessage());
            }
        return null;
    }

}
