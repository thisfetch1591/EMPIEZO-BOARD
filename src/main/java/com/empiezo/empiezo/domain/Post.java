package com.empiezo.empiezo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int views;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'TRUE'")
    private BooleanState isSecret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Likes> likes;

    @Builder
    public Post(String title, String content, String writer, User user, BooleanState isSecret) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.writer = writer;
        this.isSecret = isSecret;
        this.username = user.getUsername();
    }

    public void modify(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void deleteUserId() {
        this.user = null;
    }

    public int getLikeCount() {
        return likes.size();
    }
}
