package seminar.seminar.member.service;

import static seminar.seminar.config.AuthenticationRoles.ALL;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seminar.seminar.login.dto.LoginResponse;
import seminar.seminar.login.service.JwtTokenProvider;
import seminar.seminar.member.domain.Member;
import seminar.seminar.member.dto.AuthorityAdditionResponse;
import seminar.seminar.member.dto.MemberResponse;
import seminar.seminar.member.dto.OrganizerAdditionRequest;
import seminar.seminar.member.dto.OrganizerModifyRequest;
import seminar.seminar.member.dto.OrganizerRequest;
import seminar.seminar.member.dto.OrganizerResponse;
import seminar.seminar.member.dto.ParticipantAdditionRequest;
import seminar.seminar.member.dto.ParticipantModifyRequest;
import seminar.seminar.member.dto.ParticipantRequest;
import seminar.seminar.member.dto.ParticipantResponse;
import seminar.seminar.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public OrganizerResponse addOrganizerMember(OrganizerRequest request) {
        Member organizeMember = OrganizerRequest.toMember(request);
        validateDuplicateMember(organizeMember);
        Member member = memberRepository.save(organizeMember);
        return member.toOrganizerResponse();
    }

    @Transactional
    public ParticipantResponse addParticipant(ParticipantRequest request) {
        Member participantMember = ParticipantRequest.toMember(request.getName(), request.getBirthday(),
                request.getGender(), request.getUserId(), request.getPassword(), request.getEmail(),
                request.getRestrictedMaterial(), request.getSelfIntroduction());
        validateDuplicateMember(participantMember);
        Member member = memberRepository.save(participantMember);
        return member.toParticipantResponse();
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByUserId(member.getUserId());
        if (findMember != null) {
            throw new IllegalArgumentException("이미 존재하는 회원ID 입니다.");
        }
    }

    @Transactional
    public OrganizerResponse modifyOrganizer(OrganizerModifyRequest request, Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return findMember.modifyOrganizer(request).toOrganizerResponse();
    }

    @Transactional
    public ParticipantResponse modifyParticipant(ParticipantModifyRequest request, Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return findMember.modifyParticipant(request).toParticipantResponse();
    }

    public MemberResponse findById(Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return findMember.toMemberResponse();
    }

    @Transactional
    public AuthorityAdditionResponse addParticipant(ParticipantAdditionRequest request, Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        findMember.addParticipant(request);
        String role = ALL.name();
        String token = jwtTokenProvider.createJwtToken(findMember, role);
        return new AuthorityAdditionResponse(token);
    }

    @Transactional
    public AuthorityAdditionResponse addOrganizer(OrganizerAdditionRequest request, Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        findMember.addOrganizer(request);
        String role = ALL.name();
        String token = jwtTokenProvider.createJwtToken(findMember, role);
        return new AuthorityAdditionResponse(token);
    }
}
