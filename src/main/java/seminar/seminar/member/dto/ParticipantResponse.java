package seminar.seminar.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seminar.seminar.member.domain.Member;

@Getter
@AllArgsConstructor
public class ParticipantResponse {
    private Long id;
    private String name;

    public static ParticipantResponse toParticipant(Member member) {
        return new ParticipantResponse(member.getId(), member.getName());
    }
}
