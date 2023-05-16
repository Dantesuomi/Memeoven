package com.memeoven.memeoven.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String nameOfProfilePhoto;
    @Column(columnDefinition="TEXT")
    private String about;
    private String passwordHash;
    private Gender gender;
    private Date dateOfBirth;
    @CreationTimestamp
    private Date createdAt;


}
