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
import seminar.seminar.member.domain.Organizer;
import seminar.seminar.member.domain.Participant;
import seminar.seminar.member.dto.OrganizerRequest;
import seminar.seminar.member.dto.ParticipantRequest;
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
}
