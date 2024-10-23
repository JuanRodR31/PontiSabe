package com.pontisabe.pontisabe.Entities;

import lombok.Data;

@Data
public class Forum {
    private Long id;
    private String title;
    private Question question;
}
