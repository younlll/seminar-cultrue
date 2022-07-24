package seminar.seminar.login.service;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.Date;
import org.springframework.stereotype.Component;
import seminar.seminar.member.domain.Member;

@Component
public class JwtTokenProvider {
    private static final String SECRET_KEY = "woowahanhappyspring3th";

    public String createJwtToken(Member member) {
        Date date = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("email", member.getEmail())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
