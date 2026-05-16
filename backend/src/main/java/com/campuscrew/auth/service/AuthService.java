package com.campuscrew.auth.service;

import com.campuscrew.auth.dto.LoginRequest;
import com.campuscrew.auth.dto.LoginResponse;
import com.campuscrew.auth.dto.SignupRequest;
import com.campuscrew.auth.entity.User;
import com.campuscrew.auth.jwt.JwtTokenProvider;
import com.campuscrew.auth.repository.UserRepository;
import com.campuscrew.global.exception.CustomException;
import com.campuscrew.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public User signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .name(request.getName())
                .studentId(request.getStudentId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        String token = jwtTokenProvider.generateToken(user.getId());
        return new LoginResponse(token, user);
   }
}