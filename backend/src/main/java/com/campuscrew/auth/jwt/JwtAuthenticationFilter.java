package com.campuscrew.auth.jwt;

import com.campuscrew.auth.entity.User;
import com.campuscrew.auth.repository.UserRepository;
import com.campuscrew.global.exception.ErrorCode;
import com.campuscrew.global.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getStatus().value());
        response.getWriter().write(
                objectMapper.writeValueAsString(ApiResponse.error(errorCode))
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);

        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        TokenStatus status = jwtTokenProvider.validateToken(token);

        if(status == TokenStatus.EXPIRED) {
            sendErrorResponse(response, ErrorCode.TOKEN_EXPIRED);
            return;
        }

        if(status == TokenStatus.INVALID) {
            sendErrorResponse(response, ErrorCode.TOKEN_INVALID);
            return;
        }

        Long userId = jwtTokenProvider.getUserId(token);
        User user = userRepository.findById(userId).orElse(null);

        if(user == null) {
            sendErrorResponse(response, ErrorCode.TOKEN_INVALID);
            return;
        }
        
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
