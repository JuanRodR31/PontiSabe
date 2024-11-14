package com.pontisabe.pontisabe.Entities;

import lombok.Data;
import java.util.List;
import java.util.Date;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Boolean anonym;

    @Column(name = "question_text", nullable = false, length = 1000)
    private String questionText;

    @Temporal(TemporalType.DATE)
    private Date publishDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    public boolean isAnonym() {
        return anonym;
    }
}

