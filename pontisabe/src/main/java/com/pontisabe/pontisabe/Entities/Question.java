package com.pontisabe.pontisabe.Entities;

import lombok.Data;
import java.util.List;

@Data
public class Question {
    private Long id;
    private Long usernameId;
    private String questionText;
    private List<Answer> answers;
}
