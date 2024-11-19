package com.pontisabe.pontisabe.Entities;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;

@Data
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Boolean anonym;

    @Temporal(TemporalType.DATE)
    private Date publishDate;

    @Column(name = "reply_text", nullable = false, length = 1000)
    private String replyText;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;
}
