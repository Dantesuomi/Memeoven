package com.memeoven.memeoven.comment;


import com.memeoven.memeoven.meme.Meme;
import com.memeoven.memeoven.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;
    @ManyToOne
    @JoinColumn(name = "meme_id")
    @NotNull
    private Meme meme;
    @Column(columnDefinition="TEXT")
    @NotEmpty
    @NotNull
    private String comment;
    @CreationTimestamp
    private Date createdAt;

}

//TODO ADD LINKS FOR COMMENT AND LIKE COUNT TO ALL HTML PAGES