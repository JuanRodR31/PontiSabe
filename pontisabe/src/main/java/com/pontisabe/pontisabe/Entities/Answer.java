package com.pontisabe.pontisabe.Entities;

import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean anonym;

    @Column(name = "answer_text", nullable = false, length = 1000)
    private String answerText;

    @Temporal(TemporalType.DATE)
    private Date publishDate;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;
}
