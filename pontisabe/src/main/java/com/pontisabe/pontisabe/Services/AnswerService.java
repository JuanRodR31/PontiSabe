package com.pontisabe.pontisabe.Services;

import com.pontisabe.pontisabe.DatabaseManagement.DatabaseConnection;
import com.pontisabe.pontisabe.Entities.Answer;
import com.pontisabe.pontisabe.Entities.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AnswerService {
    AccountService accountService = new AccountService();
    //CU-02 buscar respuesta por id de pregunta
    public List<Answer> getAnswersByQuestionId(Long questionId) {
        List<Answer> answers = new ArrayList<>();
        String sql = "SELECT * FROM Answer WHERE question_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, questionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Answer answer = new Answer();
                    answer.setId(rs.getLong("id"));
                    answer.setAnswerText(rs.getString("answerText"));
                    answer.setPublishDate(rs.getDate("publishDate"));
                    boolean isAnonym = rs.getBoolean("anonym");
                    answer.setAnonym(isAnonym);
                    Long userId = rs.getLong("user_id");
                    User user = accountService.findUserById(userId);
                    if(isAnonym){
                        user.setUsername("Anonym");
                    }
                    answer.setUser(user);
                    answers.add(answer);
                }

            }
        } catch (SQLException e) {
            System.out.println("Error fetching answers: " + e.getMessage());
        }
        System.out.println("Answers found: " + answers.size());
        for (Answer answer : answers) {
            System.out.println(answer.getPublishDate());
        }
        return answers;
    }

    public boolean insertAnswer(String answerText, boolean anonym, Long userId, Long questionId) {
        String sql = "INSERT INTO Answer (answerText, publishDate, anonym, user_id, question_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Configurar los valores de los parámetros
            pstmt.setString(1, answerText);
            pstmt.setDate(2, Date.valueOf(LocalDate.now()));  // Fecha actual como fecha de publicación
            pstmt.setBoolean(3, anonym);
            pstmt.setLong(4, userId); // ID del usuario que responde
            pstmt.setLong(5, questionId); // ID de la pregunta (en este caso, es 2)

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting answer: " + e.getMessage());
            return false;
        }
    }


}
