package seminar.seminar.login.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seminar.seminar.member.common.MemberInformation;
import seminar.seminar.member.domain.Member;

class JwtTokenProviderTest {
    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @DisplayName("jwt 토큰이 정상적으로 발급되었습니다.")
    @Test
    void createJwtTokenProvideSuccess() {
        // given
        Member member = MemberInformation.createOrganizerMember();

        // when
        String token = jwtTokenProvider.createJwtToken(member);

        // then
        assertThat(token).isNotNull();
    }
}