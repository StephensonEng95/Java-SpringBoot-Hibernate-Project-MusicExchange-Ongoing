package com.musicexchange.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank(message = "username required")
    private String username;

    @NotBlank(message = "password required")
    @Size(min = 8)
    private String password;
}
