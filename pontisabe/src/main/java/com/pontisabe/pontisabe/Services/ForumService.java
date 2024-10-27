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
import java.util.stream.Collectors;

public class ForumService {

    // Método para crear un nuevo foro
    public String createForum(String title, Question question) {
        if (title == null || title.isEmpty() || question == null) {
            return ("El contenido esta vacio");
        }
        if (!addForumTODB(title, question.getId())) {
            return ("Ocurrio un error al crear el foro");
        }
        return ("Foro creado correctamente");
        
    }

    // Método para insertar el objeto Forum en la base de datos
    public boolean addForumTODB(String title, Long questionId) {
        String sql = "INSERT INTO Forum (title, question_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setLong(2, questionId);
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

    //Metodo para buscar la lista de seguidores de un usuario
    public List<Long> getFollowersByFollowing(Long followingId) {
        List<Long> followers = new ArrayList<>();
        String sql = "SELECT follower FROM Follow WHERE following = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, followingId);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    followers.add(rs.getLong("follower"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching followers: " + e.getMessage());
        }
    
    
        return followers;
    }
    
    public List<Forum> getForumsByFollowings(List<Long> followingIds) {
        List<Forum> forums = new ArrayList<>();
    
        if (followingIds.isEmpty()) {
            System.out.println("No followings found, returning empty forum list.");
            return forums;  // Retorna una lista vacía si no hay followings
        }
    
        // Construcción de la consulta SQL con placeholders
        StringBuilder sql = new StringBuilder("SELECT f.* FROM Forum f ");
        sql.append("JOIN Question q ON f.question_id = q.id ");
        sql.append("WHERE q.user_id IN (");
        sql.append(followingIds.stream().map(id -> "?").collect(Collectors.joining(", ")));
        sql.append(") AND q.anonym = 0");
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
    
            // Asigna los IDs de `following` a los placeholders en la consulta
            for (int i = 0; i < followingIds.size(); i++) {
                pstmt.setLong(i + 1, followingIds.get(i));
            }
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Forum forum = new Forum();
                    forum.setId(rs.getLong("id"));
                    forum.setTitle(rs.getString("title"));
                    Long questionId = rs.getLong("question_id");
    
                    QuestionService questionService = new QuestionService();
                    forum.setQuestion(questionService.getQuestionById(questionId));
    
                    forums.add(forum);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching forums: " + e.getMessage());
        }
        return forums;
    }
    
    public Forum getForumById(Long forumId) {
        Forum forum = null;
        String sql = "SELECT * FROM Forum WHERE id = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, forumId);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    forum = new Forum();
                    forum.setId(rs.getLong("id"));
                    forum.setTitle(rs.getString("title"));
    
                    Long questionId = rs.getLong("question_id");
                    QuestionService questionService = new QuestionService();
                    Question question = questionService.getQuestionById(questionId);
    
                    forum.setQuestion(question); // Asigna la pregunta al foro
                    // Asigna otros atributos de Forum según tu estructura de la clase
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching forum by ID: " + e.getMessage());
        }
        return forum;
    }
    

}