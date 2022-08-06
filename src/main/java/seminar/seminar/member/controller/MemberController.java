package seminar.seminar.member.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seminar.seminar.member.dto.AuthorityAdditionResponse;
import seminar.seminar.member.dto.MemberResponse;
import seminar.seminar.member.dto.OrganizerAdditionRequest;
import seminar.seminar.member.dto.OrganizerModifyRequest;
import seminar.seminar.member.dto.OrganizerRequest;
import seminar.seminar.member.dto.OrganizerResponse;
import seminar.seminar.member.dto.ParticipantAdditionRequest;
import seminar.seminar.member.dto.ParticipantModifyRequest;
import seminar.seminar.member.dto.ParticipantRequest;
import seminar.seminar.member.dto.ParticipantResponse;
import seminar.seminar.member.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/organizer")
    public ResponseEntity<OrganizerResponse> createOrganizer(
            @RequestBody @Valid OrganizerRequest organizerRequest) {
        return ResponseEntity.ok(memberService.addOrganizerMember(organizerRequest));
    }

    @PostMapping("/participant")
    public ResponseEntity<ParticipantResponse> createParticipant(
            @RequestBody @Valid ParticipantRequest participantRequest) {
        return ResponseEntity.ok(memberService.addParticipant(participantRequest));
    }

    @PatchMapping("/update/organizer/{id}")
    public ResponseEntity<OrganizerResponse> modifyOrganizer(@PathVariable Long id,
                                                             @RequestBody OrganizerModifyRequest request) {
        return ResponseEntity.ok(memberService.modifyOrganizer(request, id));
    }

    @PatchMapping("/update/participant/{id}")
    public ResponseEntity<ParticipantResponse> modifyParticipant(@PathVariable Long id,
                                                                 @RequestBody ParticipantModifyRequest request) {
        return ResponseEntity.ok(memberService.modifyParticipant(request, id));
    }

    @PatchMapping("/addition/participant/{id}")
    public ResponseEntity<AuthorityAdditionResponse> addParticipant(@PathVariable Long id,
                                                                    @RequestBody ParticipantAdditionRequest request) {
        return ResponseEntity.ok(memberService.addParticipant(request, id));
    }

    @PatchMapping("/addition/organizer/{id}")
    public ResponseEntity<AuthorityAdditionResponse> addOrganizer(@PathVariable Long id,
                                                                    @RequestBody OrganizerAdditionRequest request) {
        return ResponseEntity.ok(memberService.addOrganizer(request, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> inquireMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }
}
