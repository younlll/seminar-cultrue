package seminar.seminar.member.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import seminar.seminar.member.dto.MemberResponse;
import seminar.seminar.member.dto.OrganizerModifyRequest;
import seminar.seminar.member.dto.OrganizerResponse;
import seminar.seminar.member.dto.ParticipantModifyRequest;
import seminar.seminar.member.dto.ParticipantResponse;

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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @OneToOne(cascade = CascadeType.PERSIST)
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

    public Member(String name, String birthday, String gender, String email, Organizer organizer) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.email = email;
        this.organizer = organizer;
    }

    public Member modifyOrganizer(OrganizerModifyRequest request) {
        this.name = request.getName();
        this.birthday = request.getBirthday();
        this.gender = request.getGender();
        this.email = request.getEmail();
        this.organizer.modifyOrganizer(request.getAgency());

        return this;
    }

    public Member modifyParticipant(ParticipantModifyRequest request) {
        this.name = request.getName();
        this.birthday = request.getBirthday();
        this.gender = request.getGender();
        this.email = request.getEmail();
        this.participant.modifyParticipant(request.getRestrictedMaterial(), request.getSelfIntroduction());

        return this;
    }

    public OrganizerResponse toOrganizerResponse() {
        return new OrganizerResponse(this.id, this.name);
    }

    public ParticipantResponse toParticipantResponse() {
        return new ParticipantResponse(this.id, this.name);
    }

    public MemberResponse toMemberResponse() {
        return new MemberResponse(this.id,
                this.name,
                this.birthday,
                this.gender,
                this.userId,
                this.email,
                getAgencyOfOrganizer(),
                getRestrictedMaterialOfParticipant(),
                getSelfIntroductionOfParticipant());
    }

    private String getAgencyOfOrganizer() {
        if (this.organizer == null) {
            return null;
        }
        return this.organizer.getAgency();
    }

    private String getRestrictedMaterialOfParticipant() {
        if (this.participant == null) {
            return null;
        }
        return this.participant.getRestrictedMaterial();
    }

    private String getSelfIntroductionOfParticipant() {
        if (this.participant == null) {
            return null;
        }
        return this.participant.getSelfIntroduction();
    }
}
