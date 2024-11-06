package com.pontisabe.pontisabe.Services;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.pontisabe.pontisabe.DatabaseManagement.DatabaseConnection;
import com.pontisabe.pontisabe.Entities.Reply;
import com.pontisabe.pontisabe.Entities.User;

public class ReplyService {
    public List<Reply> getRepliesByAnswerId(Long answerId) {
        List<Reply> replies = new ArrayList<>();
        String sql = "SELECT * FROM Reply WHERE answer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, answerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reply reply = new Reply();
                    reply.setId(rs.getLong("id"));
                    reply.setReplyText(rs.getString("replyText"));
                    reply.setPublishDate(rs.getDate("publishDate"));
                    reply.setAnonym(rs.getBoolean("anonym"));

                    Long userId = rs.getLong("user_id");
                    AccountService accountService = new AccountService();
                    User user = accountService.findUserById(userId); // Método para obtener el usuario
                    reply.setUser(user);

                    replies.add(reply);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching replies: " + e.getMessage());
        }
        return replies;
    }

    public boolean insertReply(String replyText, boolean anonym, Long userId, Long answerId) {
        String sql = "INSERT INTO Reply (replyText, publishDate, anonym, user_id, answer_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, replyText);
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setBoolean(3, anonym);
            pstmt.setLong(4, userId);
            pstmt.setLong(5, answerId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting reply: " + e.getMessage());
            return false;
        }
    }
}
