package com.medcallapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter @Setter
@ToString
@Entity @Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 10000, name = "content") private String content;
    @Column(name = "created_at") @Temporal(TemporalType.TIMESTAMP) private Date createdAt;
    @Column(name = "updated_at") @Temporal(TemporalType.TIMESTAMP) private Date updatedAt;
    @ManyToOne @JoinColumn(name = "user_id") private UserEntity user;
    @ManyToOne @JoinColumn(name = "article_id") private Article article;

    public Comment(String content) {
        this.content = content;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
