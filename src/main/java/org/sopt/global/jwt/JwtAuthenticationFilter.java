package org.sopt.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sopt.global.exception.GlobalException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


/**
 * JWT 인증 필터
 * 모든 HTTP 요청마다 실행되어 JWT 토큰을 검증하고
 * SecurityContext에 인증 정보를 저장
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator jwtTokenValidator;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        try {
            String token = jwtTokenValidator.extractTokenFromHeader(request);

            if (token != null) {
                Long memberId = jwtTokenValidator.getMemberIdFromToken(token);

                if (!jwtTokenValidator.isAccessToken(token)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        memberId,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (GlobalException e) {

        }
        filterChain.doFilter(request, response);
    }

}
