package seminar.seminar.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String name;
    private String birthday;
    private String gender;
    private String userId;
    private String email;
    private String agency;
    private String restrictedMaterial;
    private String selfIntroduction;
}
