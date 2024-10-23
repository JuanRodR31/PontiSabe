package com.pontisabe.pontisabe.Entities;

import java.util.List;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String names;
    private String lastNames;
    private String email;
    private String password;
    private List<Follow> followerList;
    private List<Follow> followingList;
}
