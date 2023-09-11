package com.empiezo.empiezo.service;

import com.empiezo.empiezo.config.UserPrincipal;
import com.empiezo.empiezo.domain.BooleanState;
import com.empiezo.empiezo.domain.Role;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class OAuth2CustomUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String clientName = userRequest.getClientRegistration().getClientName();

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = null;

        if(clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }

        User user = saveOAuthMember(email);

        UserPrincipal userPrincipal = new UserPrincipal(user, oAuth2User.getAttributes());

        return userPrincipal;
    }

    private User saveOAuthMember(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            return user.get();
        }

        String nickname = email.replace("@gmail.com", "");

        User socialUser = User.builder()
                .username(email)
                .password(bCryptPasswordEncoder.encode("bimilbunho"))
                .nickname(nickname)
                .isSocial(BooleanState.TRUE)
                .email(email)
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(socialUser);

        return socialUser;
    }
}