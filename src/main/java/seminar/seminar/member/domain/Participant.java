package seminar.seminar.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long id;
    private String restrictedMaterial;
    private String selfIntroduction;

    protected Participant() {

    }

    public Participant(String restrictedMaterial, String selfIntroduction) {
        this.restrictedMaterial = restrictedMaterial;
        this.selfIntroduction = selfIntroduction;
    }

    public void modifyParticipant(String restrictedMaterial, String selfIntroduction) {
        this.restrictedMaterial = restrictedMaterial;
        this.selfIntroduction = selfIntroduction;
    }
}
