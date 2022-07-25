package seminar.seminar.login.service;

import static seminar.seminar.config.AuthenticationRoles.ALL;
import static seminar.seminar.config.AuthenticationRoles.ORGANIZER;
import static seminar.seminar.config.AuthenticationRoles.PARTICIPANT;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seminar.seminar.login.dto.LoginRequest;
import seminar.seminar.login.dto.LoginResponse;
import seminar.seminar.member.domain.Member;
import seminar.seminar.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse loginMember(LoginRequest loginRequest) {
        Member member = memberRepository.findByUserId(loginRequest.getUserId());
        validateLoginMember(loginRequest, member);
        String role = getRoleCriteria(member);
        String token = jwtTokenProvider.createJwtToken(member, role);
        return new LoginResponse(token);
    }

    private String getRoleCriteria(Member member) {
        if (member.getOrganizer() != null && member.getParticipant() != null) {
            return ALL.name();
        }

        if (member.getOrganizer() != null) {
            return ORGANIZER.name();
        }

        return PARTICIPANT.name();
    }

    private void validateLoginMember(LoginRequest loginRequest, Member member) {
        validateNotMatchedMember(member);
        validateLoginPasswordSameMemberPassword(loginRequest.getPassword(), member.getPassword());
    }

    private void validateNotMatchedMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("등록되지 않은 사용자 정보입니다.");
        }
    }

    private void validateLoginPasswordSameMemberPassword(String loginPassword, String memberPassword) {
        if (!memberPassword.equals(loginPassword)) {
            throw new IllegalArgumentException("사용자 비밀번호가 일치하지 않습니다.");
        }
    }
}
