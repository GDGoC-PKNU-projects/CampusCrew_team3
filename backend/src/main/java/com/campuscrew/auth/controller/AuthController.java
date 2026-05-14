    package com.campuscrew.auth.controller;


    import com.campuscrew.auth.dto.*;
    import com.campuscrew.auth.entity.User;
    import com.campuscrew.auth.service.AuthService;
    import com.campuscrew.global.response.ApiResponse;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/auth")
    @RequiredArgsConstructor
    public class AuthController {
        private final AuthService authService;

        @PostMapping("/signup")
        public ResponseEntity<ApiResponse<?>> signup(@RequestBody @Valid SignupRequest request) {
            User user = authService.signup(request);
            return ResponseEntity.ok(ApiResponse.success(new SignupResponse(user), "회원가입이 완료되었습니다."));
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<?>> login(@RequestBody @Valid LoginRequest request) {
            LoginResponse loginResponse = authService.login(request);
            return ResponseEntity.ok(ApiResponse.success(loginResponse, "로그인에 성공했습니다."));
        }

        @GetMapping("/me")
        public ResponseEntity<ApiResponse<?>> me(HttpServletRequest request) {
            User user = (User) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();

            return ResponseEntity.ok(ApiResponse.success(new MeResponse(user)));
        }
    }