package com.memeoven.memeoven.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileDto {
    @NotNull
    private Date date;
    private Gender gender;
    private String description;
    //private MultipartFile file;
}
