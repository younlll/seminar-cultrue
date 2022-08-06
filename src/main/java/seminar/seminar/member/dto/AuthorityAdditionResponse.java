package seminar.seminar.member.dto;

import lombok.Getter;

@Getter
public class AuthorityAdditionResponse {
    private final String accessToken;

    public AuthorityAdditionResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
