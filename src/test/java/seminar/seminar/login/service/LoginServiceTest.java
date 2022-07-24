package seminar.seminar.login.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seminar.seminar.login.dto.LoginRequest;
import seminar.seminar.member.common.MemberInformation;
import seminar.seminar.member.domain.Member;
import seminar.seminar.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private LoginService loginService;

    @DisplayName("등록된 로그인 userId가 아닌 경우, 예외처리 된다.")
    @Test
    void notMatchedUserIdThenLoginFail() {
        // given
        Member member = MemberInformation.createOrganizerMember();
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        memberRepository.save(member);

        // when & then
        LoginRequest loginRequest = new LoginRequest("wrongId", "password");
        assertThatThrownBy(() -> loginService.loginMember(loginRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("비밀번호가 일치하지 않는 경우, 예외처리 된다.")
    @Test
    void notMatchedPasswordThenLoginFail() {
        // given
        Member member = MemberInformation.createOrganizerMember();
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        memberRepository.save(member);

        // when & then
        LoginRequest loginRequest = new LoginRequest("younlll", "pass");
        when(memberRepository.findByUserId(loginRequest.getUserId())).thenReturn(member);
        assertThatThrownBy(() -> loginService.loginMember(loginRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
