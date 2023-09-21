package com.empiezo.empiezo.security;

import com.empiezo.empiezo.domain.BooleanState;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCustomDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
        if (user.getIsDeleted() == BooleanState.TRUE) {
            throw new UsernameNotFoundException("계정이 삭제되었습니다.");
        }

        return new UserPrincipal(user);
    }
}
