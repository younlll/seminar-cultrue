package seminar.seminar.member.common;

import seminar.seminar.member.domain.Member;
import seminar.seminar.member.domain.Organizer;
import seminar.seminar.member.domain.Participant;
import seminar.seminar.member.dto.OrganizerRequest;
import seminar.seminar.member.dto.ParticipantRequest;

public class MemberInformation {
    public static OrganizerRequest createOrganizerRequest() {
        return new OrganizerRequest("name", "19960103", "F", "younlll", "password",
                "email@gmail.com", "agency");
    }

    public static ParticipantRequest createParticipantRequest() {
        return new ParticipantRequest("name", "19960103", "F", "younlll", "password",
                "email@gmail.com", "cucumber", "hi, there");
    }

    public static Member createOrganizerMember() {
        return new Member("name", "19960103", "F", "younlll", "password", "email@gmail.com", new Organizer("agency"));
    }

    public static Member createParticipantMember() {
        return new Member("name", "19960103", "F", "younlll", "password", "email@gmail.com",
                new Participant("cucumber", "hi, there"));
    }
}
