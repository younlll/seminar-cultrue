package seminar.seminar.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seminar.seminar.member.domain.Member;
import seminar.seminar.member.dto.OrganizerRequest;
import seminar.seminar.member.dto.OrganizerResponse;
import seminar.seminar.member.dto.ParticipantRequest;
import seminar.seminar.member.dto.ParticipantResponse;
import seminar.seminar.member.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public OrganizerResponse addOrganizerMember(OrganizerRequest request) {
        Member organizeMember = OrganizerRequest.toMember(request.getName(), request.getBirthday(), request.getGender(),
                request.getUserId(), request.getPassword(), request.getEmail(), request.getAgency());
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
        Member findMembers = memberRepository.findByUserId(member.getUserId());
        if (findMembers != null) {
            throw new IllegalArgumentException("이미 존재하는 회원ID 입니다.");
        }
    }
}
