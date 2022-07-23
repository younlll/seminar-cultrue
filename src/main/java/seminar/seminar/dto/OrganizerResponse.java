package seminar.seminar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seminar.seminar.domain.Member;

@Getter
@AllArgsConstructor
public class OrganizerResponse {
    private Long id;
    private String name;

    public static OrganizerResponse toOrganizer(Member member){
        return new OrganizerResponse(member.getId(), member.getName());
    }
}
