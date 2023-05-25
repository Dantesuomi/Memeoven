package com.memeoven.memeoven.meme;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemeDto {
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    private Category category;
    @NotNull
    private MultipartFile file;
}
