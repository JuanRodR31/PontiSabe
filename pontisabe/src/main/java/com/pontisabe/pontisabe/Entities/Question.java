package com.pontisabe.pontisabe.Entities;

import lombok.Data;
import java.util.List;

@Data
public class Question {
    private Long id;
    private User user;
    private String questionText;
    private List<Answer> answers;
}
