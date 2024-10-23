package com.pontisabe.pontisabe.Entities;

import java.util.List;

import lombok.Data;

@Data
public class Answer {
    private Long id;
    private Long userId;
    private String answerText;
    List<Reply> replies;
    
}
