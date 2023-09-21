package com.empiezo.empiezo.security.handler;

import com.empiezo.empiezo.exception.UserNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    private final String DEFAULT_FAILURE_URL = "/login?error=true";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = null;

        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "계정이 삭제되었습니다.";
        }
        else {
            errorMessage = exception.getMessage();
        }

        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher(DEFAULT_FAILURE_URL).forward(request, response);
    }
}
