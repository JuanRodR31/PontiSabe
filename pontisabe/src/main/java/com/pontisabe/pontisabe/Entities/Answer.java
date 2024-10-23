package com.pontisabe.pontisabe.Entities;

import java.util.List;
import java.util.Date;
import lombok.Data;

@Data
public class Answer {
    private Long id;
    private User user;
    private Boolean anonym;
    private String answerText;
    private Date pusblishDate;
    List<Reply> replies;    
}
