package org.sopt.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberResponse;
import org.sopt.member.entity.Gender;
import org.sopt.member.entity.Member;
import org.sopt.member.exception.MemberException;
import org.sopt.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//  Mockito 사용 준비
@ExtendWith(MockitoExtension.class)
@DisplayName("MemberService 단위 테스트")
class MemberServiceTest {

    // 가짜 객체 생성
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    // MemberService에 위 Mock들 자동 주입
    @InjectMocks
    private MemberService memberService;

    @DisplayName("이메일 중복이 없으면 회원가입에 성공한다.")
    @Test
    void joinSuccess() {
        // given 준비
        MemberCreateRequest request = new MemberCreateRequest(
                "test",
                "2001-12-01",
                "test@example.com",
                Gender.MALE,
                "1234"
        );
        // 이메일 중복 없다고 가정
        when(memberRepository.existsByEmail("test@example.com"))
                .thenReturn(false);
        // 비밀번호 암호화 시
        when(passwordEncoder.encode("1234"))
                .thenReturn("encoded_password_1234");
        when(memberRepository.save(any(Member.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when 실행
        MemberResponse response = memberService.join(request);

        // then 검증
        assertThat(response).isNotNull();
        assertThat(response)
                .extracting("name","email")
                .containsExactly("test","test@example.com");
    }

    @DisplayName("미성년자가 회원가입을 시도하면 예외가 발생한다.")
    @Test
    void joinFailWithUnderAge() {
        // given 준비
        MemberCreateRequest request = new MemberCreateRequest(
                "test",
                "2010-10-10",
                "test@example.com",
                Gender.FEMALE,
                "1234"
        );

        when(memberRepository.existsByEmail("test@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("1234"))
                .thenReturn("encoded_password_1234");

        // when & then 검증
        assertThatThrownBy(() -> memberService.join(request))
                .isInstanceOf(MemberException.class);
    }

    @DisplayName("중복된 이메일로 회원가입을 시도하면 예외가 발생한다")
    @Test
    void joinFailWithDuplicateEmail() {
        // given 준비
        MemberCreateRequest request = new MemberCreateRequest(
                "test",
                "2001-12-01",
                "test@example.com",
                Gender.MALE,
                "1234"
        );
        when(memberRepository.existsByEmail("test@example.com"))
                .thenReturn(true);

        // when & then 검증
        assertThatThrownBy(() -> memberService.join(request))
                .isInstanceOf(MemberException.class);
    }

    @DisplayName("회원 ID로 회원을 조회할 수 있다")
    @Test
        void findMemberSuccess() {
        // given 준비
        Member member = createTestMember();

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        // when 실행
        MemberResponse response = memberService.findOne(member.getId());

        // then 검증
        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("test");
    }

    @DisplayName("존재하지 않는 회원 ID로 조회하면 예외가 발생한다")
    @Test
    void findMemberFailWithNotFound() {
        // given 준비
        when(memberRepository.findById(999L))
                .thenReturn(Optional.empty());

        // when & then 검증
        assertThatThrownBy(() -> memberService.findOne(999L))
                .isInstanceOf(MemberException.class);
    }

    @DisplayName("회원 ID로 회원을 삭제할 수 있다")
    @Test
    void deleteMemberSuccess() {
        // given 준비
        Member member = createTestMember();

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        // when 실행
        memberService.deleteMember(1L);

        // then 검증
        // 메서드가 제대로 호출되었는지 검증
        verify(memberRepository).deleteById(1L);
    }

    @DisplayName("존재하지 않는 회원 ID로 삭제하면 예외가 발생한다")
    @Test
    void deleteMemberFailWithNotFound() {
        // given 준비
        when(memberRepository.findById(999L))
                .thenReturn(Optional.empty());

        // when & then 검증
        assertThatThrownBy(() -> memberService.deleteMember(999L))
                .isInstanceOf(MemberException.class);
    }

    private Member createTestMember() {
        return Member.builder()
                .id(1L)
                .name("test")
                .birth("2001-12-01")
                .email("test@example.com")
                .gender(Gender.MALE)
                .password("encoded_password")
                .build();
    }
}