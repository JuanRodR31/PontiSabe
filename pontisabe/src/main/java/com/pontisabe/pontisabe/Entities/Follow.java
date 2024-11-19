package com.pontisabe.pontisabe.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "following_user_id", nullable = false)
    private User following;

    @ManyToOne
    @JoinColumn(name = "follower_user_id", nullable = false)
    private User follower;
}

