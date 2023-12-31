package com.empiezo.empiezo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length= 50, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BooleanState isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BooleanState isSocial;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Likes> likes;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Image image;
    public User update(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        return this;
    }

    public void setDelete() {
        this.isDeleted = BooleanState.TRUE;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
