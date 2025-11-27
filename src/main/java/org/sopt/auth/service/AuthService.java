package org.sopt.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.auth.dto.request.LoginRequest;
import org.sopt.auth.dto.response.TokenResponse;
import org.sopt.auth.exception.AuthErrorCode;
import org.sopt.auth.exception.AuthException;
import org.sopt.global.jwt.JwtService;
import org.sopt.member.entity.Member;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.exception.MemberException;
import org.sopt.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse login(LoginRequest request) {

        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new AuthException(AuthErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtService.generateAccessToken(member.getId(),member.getEmail());
        String refreshToken = jwtService.generateRefreshToken(member.getId(),member.getEmail());

        return TokenResponse.of(accessToken, refreshToken);
    }
}
