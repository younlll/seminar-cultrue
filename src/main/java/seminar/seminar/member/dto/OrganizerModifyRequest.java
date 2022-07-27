package seminar.seminar.member.dto;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizerModifyRequest {
    private String name;
    private String birthday;
    private String gender;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    private String agency;
}
