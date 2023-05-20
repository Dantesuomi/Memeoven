package com.memeoven.memeoven.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewUser {
    private String username;
    private String email;
    private String password;
}
