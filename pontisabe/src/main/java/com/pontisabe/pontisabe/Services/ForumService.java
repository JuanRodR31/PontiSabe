package com.pontisabe.pontisabe.Services;

import com.pontisabe.pontisabe.DatabaseManagement.DatabaseConnection;
import com.pontisabe.pontisabe.Entities.Forum;
import com.pontisabe.pontisabe.Entities.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ForumService {

    // Método para crear un nuevo foro
    public String createForum(String title, Question question) {
        if (title == null || title.isEmpty() || question == null) {
            return ("El contenido esta vacio");
        }
        Forum forum = new Forum();
        forum.setTitle(title);
        forum.setQuestion(question);
        if (!addForumTODB(forum)) {
            return ("Ocurrio un error al crear el foro");
        }
        return ("Foro creado correctamente");
        
    }

    // Método para insertar el objeto Forum en la base de datos
    public boolean addForumTODB(Forum forum) {
        String sql = "INSERT INTO Forum (title, question_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, forum.getTitle());
            pstmt.setLong(2, forum.getQuestion().getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting forum: " + e.getMessage());
            return false;
        }
    }

    //Método para obtener todos los foros
    public List<Forum> getAllForums(){
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT * FROM Forum";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                QuestionService questionService = new QuestionService();
                Forum forum = new Forum();
                forum.setId(rs.getLong("id"));
                forum.setTitle(rs.getString("title"));
                Long questionId=(rs.getLong("question_id"));
                Question question=questionService.getQuestionById(questionId);
                forum.setQuestion(question);
                forums.add(forum);

            }
            return forums;
        }
        catch (SQLException e) {
            System.out.println("Error getting forums: " + e.getMessage());
            return null;
        }
    }

}