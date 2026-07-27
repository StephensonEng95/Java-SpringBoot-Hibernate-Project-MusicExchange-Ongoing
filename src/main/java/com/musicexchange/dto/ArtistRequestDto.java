package com.musicexchange.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistRequestDto {

    @NotBlank(message = "username required")
    @Size(min = 3, max = 50, message = "username should be between 3 and 50 chars long")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "email required")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password should be minimum 8 chars long")
    private String password;
}
