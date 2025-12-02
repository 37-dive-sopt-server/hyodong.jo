package org.sopt.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.auth.dto.request.LoginRequest;
import org.sopt.auth.dto.response.KakaoUserInfoResponse;
import org.sopt.auth.dto.response.TokenResponse;
import org.sopt.auth.exception.AuthErrorCode;
import org.sopt.auth.exception.AuthException;
import org.sopt.global.exception.GlobalException;
import org.sopt.global.exception.errorcode.GlobalErrorCode;
import org.sopt.global.jwt.JwtTokenProvider;
import org.sopt.global.jwt.JwtTokenValidator;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenValidator jwtTokenValidator;
    private final PasswordEncoder passwordEncoder;
    private final KakaoService kakaoService;

    public TokenResponse login(LoginRequest request) {

        Member member = memberRepository.findByEmailAndProvider(request.email(),"LOCAL")
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if(member.getPassword() == null || !passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new AuthException(AuthErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(member.getId(), member.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(member.getId(), member.getEmail());

        return TokenResponse.of(accessToken, refreshToken);
    }

    public TokenResponse kakaoLogin(String code){

        // 1. 인가 코드로 카카오 액세스 토큰 받기
        String kakaoAccessToken = kakaoService.getAccessToken(code);

        // 2. 카카오 액세스 토큰으로 사용자 정보 가져오기
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoService.getUserInfo(kakaoAccessToken);

        // 3. 카카오 사용자 정보로 회원가입 또는 로그인 처리
        Member member = findOrCreateMember(kakaoUserInfoResponse);

        // 4. JWT 토큰 발급
        TokenResponse tokenResponse = issueTokens(member);

        return tokenResponse;
    }

    public TokenResponse refreshToken(String refreshToken) {
        // 1. 리프레시 토큰인지 검증, 타입 확인, id추출
        Long memberId = jwtTokenValidator.getMemberIdFromRefreshToken(refreshToken);

        // 2. 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 3. 새로운 액세스 토큰 발급
        String newAccessToken = jwtTokenProvider.generateAccessToken(
                member.getId(),
                member.getEmail()
        );

        return TokenResponse.of(newAccessToken, refreshToken);
    }


    private Member findOrCreateMember(KakaoUserInfoResponse kakaoUserInfoResponse) {

        return memberRepository.findByProviderId(kakaoUserInfoResponse.id())
                .orElseGet(() -> {
                    Member newMember = Member.createKakaoMember(
                            kakaoUserInfoResponse.email(),
                            kakaoUserInfoResponse.nickname(),
                            kakaoUserInfoResponse.id()
                    );

                    return memberRepository.save(newMember);
                });
    }


    private TokenResponse issueTokens(Member member) {

        String accessToken = jwtTokenProvider.generateAccessToken(
                member.getId(),
                member.getEmail()
        );

        String refreshToken = jwtTokenProvider.generateRefreshToken(
                member.getId(),
                member.getEmail()
        );

        return TokenResponse.of(accessToken, refreshToken);
    }
}

