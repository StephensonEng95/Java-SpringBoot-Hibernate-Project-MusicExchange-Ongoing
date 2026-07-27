package com.musicexchange.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongRequestDto {

    @NotBlank(message = "song title required")
    @Size(min = 50, message = "song title should be minimum 50 chars long")
    private String title;

    @NotBlank(message = "song genre required")
    @Size(min = 10)
    private String genre;

    @NotNull(message = "audio file required")
    private MultipartFile audioFile;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @NotNull
    private Long artistId;

}
