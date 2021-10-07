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
@Entity
@Table(name = "articles")
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, length = 10000, name = "content")
    private String content;

    @Column(nullable = false, name = "speciality")
    private String speciality;

    @Column(name = "validated")
    private boolean validated;

    @Column(name = "created_at") @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at") @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public Article(String title, String content, String speciality) {
        this.title = title;
        this.content = content;
        this.speciality = speciality;
        this.validated = false;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
