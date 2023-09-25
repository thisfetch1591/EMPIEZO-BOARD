package com.empiezo.empiezo.dto;

import com.empiezo.empiezo.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


public class UserDto {

    @Data
    @Builder
    public static class RegisterRequest {
        @NotBlank(message = "아이디를 입력해주시기 바랍니다.")
        @Size(min = 6, max = 10, message = "아이디는 6~10글자 이여야 합니다.")
        private String username;

        @NotBlank(message = "패스워드를 입력해주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20글자 이여야 합니다.")
        private String password;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(min = 3, max = 8, message = "닉네임은 3~8글자 이여야 합니다.")
        private String nickname;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식이 아닙니다.")
        private String email;
    }

    @Data
    @Builder
    public static class AuthenticationRequest {

        @NotBlank(message = "아이디를 입력해주시기 바랍니다.")
        @Size(min = 6, max = 10, message = "아이디는 6~10글자 이여야 합니다.")
        private String username;

        @NotBlank(message = "패스워드를 입력해주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20글자 이여야 합니다.")
        private String password;
    }

    @Data
    public static class ModifyRequest {

        private Long id;
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(min = 3, max = 8, message = "닉네임은 3~8글자 이여야 합니다.")
        private String nickname;

        @NotBlank(message = "패스워드를 입력해주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20글자 이여야 합니다.")
        private String password;

        @Builder
        public ModifyRequest(Long id, String nickname, String password) {
            this.id = id;
            this.nickname = nickname;
            this.password = password;
        }
    }

    @Data
    public static class Response {
        private final Long id;
        private final String username;
        private final String password;
        private final String nickname;

        private final String profileImage;
        private final String email;

        @Builder
        public Response(User user) {
            this.id = user.getId();
            this.password = user.getPassword();
            this.username = user.getUsername();
            this.nickname = user.getNickname();
            if(user.getImage() == null) {
                this.profileImage = null;
            } else {
                this.profileImage = user.getImage().getUrl();
            }
            this.email = user.getEmail();
        }
    }
}
