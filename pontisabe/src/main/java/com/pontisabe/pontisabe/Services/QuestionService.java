package com.pontisabe.pontisabe.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pontisabe.pontisabe.DatabaseManagement.DatabaseConnection;
import com.pontisabe.pontisabe.Entities.Question;

public class QuestionService {
    //CU-01 Crear Pregunta

    public Question getQuestionById(Long id) {
    String sql = "SELECT * FROM Question WHERE id = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Crear un objeto Question a partir de los datos obtenidos
                    Question question = new Question();
                    question.setId(rs.getLong("id"));
                    question.setQuestionText(rs.getString("questionText"));
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

}
