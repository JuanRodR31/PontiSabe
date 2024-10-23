package com.pontisabe.pontisabe.Entities;

import lombok.Data;
import java.util.Date;

@Data
public class Reply {
    private Long id;
    private User user;
    private Boolean anonym;
    private Date publishDate;
    private String replyText;
}
