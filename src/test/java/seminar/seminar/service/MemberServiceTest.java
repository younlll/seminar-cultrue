package seminar.seminar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seminar.seminar.domain.Member;
import seminar.seminar.domain.Organizer;
import seminar.seminar.domain.Participant;
import seminar.seminar.dto.OrganizerRequest;
import seminar.seminar.dto.OrganizerResponse;
import seminar.seminar.dto.ParticipantRequest;
import seminar.seminar.dto.ParticipantResponse;
import seminar.seminar.repository.MemberRepository;

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
        OrganizerRequest request = new OrganizerRequest("name", "19960103", "F", "younlll", "password",
                "email@gmail.com", "agency");
        Member member = new Member("name", "19960103", "F", "younlll", "password", "email@gmail.com",
                new Organizer("agency"));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        OrganizerResponse response = memberService.addOrganizerMember(request);

        // then
        assertThat(response.getName()).isEqualTo(member.getName());
    }

    @DisplayName("모임참여자 정보를 저장하는데 성공했습니다.")
    @Test
    void addParticipantMemberSuccess() {
        // given
        ParticipantRequest request = new ParticipantRequest("name", "19960103", "F", "younlll", "password",
                "email@gmail.com", "cucumber", "hi, there");
        Member member = new Member("name", "19960103", "F", "younlll", "password", "email@gmail.com",
                new Participant("cucumber", "hi, there"));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        ParticipantResponse response = memberService.addParticipant(request);

        // then
        assertThat(response.getName()).isEqualTo(member.getName());
    }
}
