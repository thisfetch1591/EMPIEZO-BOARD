package com.empiezo.empiezo.security;

import com.empiezo.empiezo.domain.BooleanState;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserPrincipal extends User implements OAuth2User, Serializable {

    private final Long userId;

    private Map<String, Object> attr;

    public UserPrincipal(com.empiezo.empiezo.domain.User user) {
        super(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().toString())));
        this.userId = user.getId();
    }

    public UserPrincipal(com.empiezo.empiezo.domain.User user, Map<String, Object> attr) {
        this(user);
        this.attr = attr;
    }

    public Long getUserId() {
        return userId;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public String getName() {
        return super.getUsername();
    }
}
