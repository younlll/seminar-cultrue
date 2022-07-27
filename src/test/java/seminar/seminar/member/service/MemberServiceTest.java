package seminar.seminar.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seminar.seminar.member.common.MemberInformation;
import seminar.seminar.member.domain.Member;
import seminar.seminar.member.dto.OrganizerModifyRequest;
import seminar.seminar.member.dto.OrganizerRequest;
import seminar.seminar.member.dto.OrganizerResponse;
import seminar.seminar.member.dto.ParticipantModifyRequest;
import seminar.seminar.member.dto.ParticipantRequest;
import seminar.seminar.member.dto.ParticipantResponse;
import seminar.seminar.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @DisplayName("모임주최자 정보를 저장하는데 성공했습니다.")
    @Test
    void addOrganizerMemberSuccess() {
        // given
        OrganizerRequest request = MemberInformation.createOrganizerRequest();
        Member member = MemberInformation.createOrganizerMember();
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

        // when
        memberService.addOrganizerMember(request);

        // then
        Member response = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        assertThat(response.getOrganizer().getAgency()).isEqualTo(member.getOrganizer().getAgency());
    }

    @DisplayName("모임참여자 정보를 저장하는데 성공했습니다.")
    @Test
    void addParticipantMemberSuccess() {
        // given
        ParticipantRequest request = MemberInformation.createParticipantRequest();
        Member member = MemberInformation.createParticipantMember();
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

        // when
        memberService.addParticipant(request);

        // then
        Member response = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        assertThat(response.getName()).isEqualTo(member.getName());
    }

    @DisplayName("중복된 회원ID 가입시, 예외처리 된다.")
    @Test
    void addMemberFailWithDuplicationUserId() {
        // given
        ParticipantRequest participantRequest = MemberInformation.createParticipantRequest();
        Member participant = MemberInformation.createOrganizerMember();
        OrganizerRequest organizerRequest = MemberInformation.createOrganizerRequest();
        when(memberRepository.save(any(Member.class))).thenReturn(participant);
        memberService.addParticipant(participantRequest);

        when(memberRepository.findByUserId(any(String.class))).thenReturn(participant);

        // when & then
        assertThatThrownBy(() -> memberService.addOrganizerMember(organizerRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주최자의 정보가 정상적으로 수정되었습니다.")
    @Test
    void modifyOrganizerSuccess() {
        // given
        OrganizerRequest request = MemberInformation.createOrganizerRequest();
        Member member = MemberInformation.createOrganizerMember();
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));
        OrganizerResponse organizerResponse = memberService.addOrganizerMember(request);

        // when
        OrganizerModifyRequest organizerModifyRequest = new OrganizerModifyRequest("name2", "19960103", "F",
                "email2@gmail.com", "agency2");
        Member modifiedMember = member.modifyOrganizer(organizerModifyRequest);
        when(memberRepository.findById(organizerResponse.getId())).thenReturn(Optional.of(member));
        memberService.modifyOrganizer(organizerModifyRequest, organizerResponse.getId());

        // then
        Member response = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        assertThat(response.getName()).isEqualTo(modifiedMember.getName());
    }

    @DisplayName("참여자의 정보가 정상적으로 수정되었습니다.")
    @Test
    void modifyParticipantSuccess() {
        // given
        ParticipantRequest request = MemberInformation.createParticipantRequest();
        Member member = MemberInformation.createParticipantMember();
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));
        ParticipantResponse participantResponse = memberService.addParticipant(request);

        // when
        ParticipantModifyRequest participantModifyRequest = new ParticipantModifyRequest("name2", "19960103", "F",
                "email2@gmail.com", "mint", "lol");
        Member modifiedMember = member.modifyParticipant(participantModifyRequest);
        when(memberRepository.findById(participantResponse.getId())).thenReturn(Optional.of(member));
        memberService.modifyParticipant(participantModifyRequest, participantResponse.getId());

        // then
        Member response = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        assertThat(response.getName()).isEqualTo(modifiedMember.getName());
    }

    @DisplayName("수정하려는 멤버의 정보가 존재하지 않는 경우, 예외처리 된다.")
    @Test
    void exceptionNotMatchedModifyParticipant() {
        // when & then
        when(memberRepository.findById(any(Long.class))).thenThrow(IllegalArgumentException.class);
        ParticipantModifyRequest participantModifyRequest = new ParticipantModifyRequest("name2", "19960103", "F",
                "email2@gmail.com", "mint", "lol");
        assertThatThrownBy(() -> memberService.modifyParticipant(participantModifyRequest, 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
