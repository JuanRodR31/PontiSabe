package com.pontisabe.pontisabe.Entities;

import lombok.Data;

@Data
public class Reply {
    private Long id;
    private User user;
    private Boolean anonym;
    private String replyText;
}
