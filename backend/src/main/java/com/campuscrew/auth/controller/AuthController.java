    package com.campuscrew.auth.controller;


    import com.campuscrew.auth.dto.*;
    import com.campuscrew.auth.entity.User;
    import com.campuscrew.auth.service.AuthService;
    import com.campuscrew.global.exception.SuccessCode;
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
            return ResponseEntity
                    .status(SuccessCode.SIGNUP_SUCCESS.getStatus())
                    .body(ApiResponse.success(SuccessCode.SIGNUP_SUCCESS, new SignupResponse(user)));
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<?>> login(@RequestBody @Valid LoginRequest request) {
            LoginResponse loginResponse = authService.login(request);
            return ResponseEntity.ok(ApiResponse.success(SuccessCode.LOGIN_SUCCESS, loginResponse));
        }

        @GetMapping("/me")
        public ResponseEntity<ApiResponse<?>> me(HttpServletRequest request) {
            User user = (User) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();

            return ResponseEntity.ok(ApiResponse.success(SuccessCode.ME_SUCCESS, new MeResponse(user)));
        }
    }