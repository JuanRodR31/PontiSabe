package com.pontisabe.pontisabe.Entities;

import lombok.Data;
import java.util.List;
import java.sql.Date;
@Data
public class Question {
    private Long id;
    private User user;
    private Boolean anonym;
    private String questionText;
    private Date publishDate;
    private List<Answer> answers;

    public boolean isAnonym() {
        return anonym;
    }
}
