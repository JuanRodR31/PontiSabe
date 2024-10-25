package com.pontisabe.pontisabe.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

import com.pontisabe.pontisabe.DatabaseManagement.DatabaseConnection;
import com.pontisabe.pontisabe.Entities.Question;
import com.pontisabe.pontisabe.Entities.User;

public class QuestionService {
    //CU-01 Crear Pregunta
    
    public boolean createQuestion(Question question) {
        String sql = "INSERT INTO Question (questionText, publishDate, anonym, user_id) VALUES (?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, question.getQuestionText());
            Date date = Date.valueOf(LocalDate.now());
            pstmt.setDate(2, date);
            pstmt.setBoolean(3, question.isAnonym());
            if (question.getUser() != null) {
                pstmt.setLong(4, question.getUser().getId());
            } else {
                pstmt.setNull(4, java.sql.Types.BIGINT);
            }
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.out.println("Error creating question: " + e.getMessage());
        }
        return false;
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
