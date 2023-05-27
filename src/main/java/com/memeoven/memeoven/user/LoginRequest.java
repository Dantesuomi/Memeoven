package com.memeoven.memeoven.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String username;
}
