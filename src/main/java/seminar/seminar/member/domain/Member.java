package seminar.seminar.member.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String birthday;
    private String gender;
    private String userId;
    private String password;
    private String email;

    @OneToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    protected Member() {

    }

    public Member(String name, String birthday, String gender, String userId, String password, String email,
                  Organizer organizer) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.organizer = organizer;
    }

    public Member(String name, String birthday, String gender, String userId, String password, String email,
                  Participant participant) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.participant = participant;
    }
}
