package com.pontisabe.pontisabe.Entities;

import lombok.Data;

@Data
public class Follow {
    private User following;
    private User follower;
}
