package com.pontisabe.pontisabe.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "forums")  
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;
}
