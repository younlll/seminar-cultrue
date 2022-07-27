package seminar.seminar.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seminar.seminar.member.domain.Member;
import seminar.seminar.member.dto.OrganizerModifyRequest;
import seminar.seminar.member.dto.OrganizerRequest;
import seminar.seminar.member.dto.OrganizerResponse;
import seminar.seminar.member.dto.ParticipantModifyRequest;
import seminar.seminar.member.dto.ParticipantRequest;
import seminar.seminar.member.dto.ParticipantResponse;
import seminar.seminar.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public OrganizerResponse addOrganizerMember(OrganizerRequest request) {
        Member organizeMember = OrganizerRequest.toMember(request);
        validateDuplicateMember(organizeMember);
        Member member = memberRepository.save(organizeMember);
        return OrganizerResponse.toOrganizer(member);
    }

    @Transactional
    public ParticipantResponse addParticipant(ParticipantRequest request) {
        Member participantMember = ParticipantRequest.toMember(request.getName(), request.getBirthday(),
                request.getGender(), request.getUserId(), request.getPassword(), request.getEmail(),
                request.getRestrictedMaterial(), request.getSelfIntroduction());
        validateDuplicateMember(participantMember);
        Member member = memberRepository.save(participantMember);
        return ParticipantResponse.toParticipant(member);
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
        return OrganizerResponse.toOrganizer(findMember.modifyOrganizer(request));
    }

    @Transactional
    public ParticipantResponse modifyParticipant(ParticipantModifyRequest request, Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return ParticipantResponse.toParticipant(findMember.modifyParticipant(request));
    }
}
