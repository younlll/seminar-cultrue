package seminar.seminar.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import seminar.seminar.member.domain.Member;
import seminar.seminar.member.domain.Participant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantRequest {
    @NotBlank(message = "회원 이름 입력은 필수입니다.")
    private String name;
    private String birthday;
    private String gender;

    @NotBlank(message = "회원 아이디 입력은 필수입니다.")
    private String userId;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    @Length(min = 6, max = 15, message = "비밀번호의 길이는 6자~15자입니다.")
    private String password;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일 입력은 필수입니다.")
    private String email;

    private String restrictedMaterial;
    private String selfIntroduction;

    public static Member toMember(String name, String birthday, String gender, String userId, String password,
                                  String email, String restrictedMaterial, String selfIntroduction) {
        return new Member(name, birthday, gender, userId, password, email,
                new Participant(restrictedMaterial, selfIntroduction));
    }
}
