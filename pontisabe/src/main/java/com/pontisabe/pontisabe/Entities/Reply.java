package com.pontisabe.pontisabe.Entities;

import lombok.Data;

@Data
public class Reply {
    private Long id;
    private Long userId;
    private String replyText;
}
