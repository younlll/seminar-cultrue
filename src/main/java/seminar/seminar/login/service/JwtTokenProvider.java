package seminar.seminar.login.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seminar.seminar.member.domain.Member;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private static final String SECRET_KEY = "woowahanhappyspring3th";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CLAIM_ROLES = "roles";

    public String createJwtToken(Member member, String role) {
        Date date = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(date)
                .setExpiration(createExpireDate(date))
                .setSubject(member.getEmail())
                .claim(CLAIM_ROLES, role)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private Date createExpireDate(Date date) {
        return new Date(date.getTime() + Duration.ofMinutes(30).toMillis());
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    public boolean validateToken(String token) {
        return !parseJwtToken(token).getExpiration().before(new Date());
    }

    public String getTokenRole(String token) {
        return parseJwtToken(token).get(CLAIM_ROLES).toString();
    }

    private Claims parseJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(bearerRemove(token))
                .getBody();
    }

    private String bearerRemove(String token) {
        return token.substring(TOKEN_PREFIX.length());
    }

    public String getSubject(String token) {
        return parseJwtToken(token).getSubject();
    }
}
