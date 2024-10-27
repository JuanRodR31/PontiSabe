package com.pontisabe.pontisabe.Entities;

import java.util.List;
import java.sql.Date;
import lombok.Data;

@Data
public class Answer {
    private Long id;
    private User user;
    private Boolean anonym;
    private String answerText;
    private Date publishDate;
    List<Reply> replies;
}
