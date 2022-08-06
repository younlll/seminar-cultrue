package seminar.seminar.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantAdditionRequest {
    private String restrictedMaterial;
    private String selfIntroduction;
}
