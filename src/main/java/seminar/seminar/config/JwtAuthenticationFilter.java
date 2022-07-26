package seminar.seminar.config;

import static seminar.seminar.config.AuthenticationRoles.ORGANIZER;
import static seminar.seminar.config.AuthenticationRoles.PARTICIPANT;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seminar.seminar.exceptions.IllegalPermissionRequestException;
import seminar.seminar.login.service.JwtTokenProvider;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter implements Filter {
    private static final Map<String, String> authenticationPath = Map.of(
            ORGANIZER.name(), "/update/organizer",
            PARTICIPANT.name(), "/update/participant"
    );
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String roles = jwtTokenProvider.getTokenRole(token);
            validateRoles(request, roles);
            request.setAttribute("email", jwtTokenProvider.getSubject(token));
        }
        request.setAttribute("email", jwtTokenProvider.getSubject(token));
        chain.doFilter(request, response);
    }

    private void validateRoles(HttpServletRequest request, String roles) {
        if (!request.getRequestURI().contains(authenticationPath.get(roles)) && !request.getRequestURI()
                .contains(authenticationPath.get(roles))) {
            throw new IllegalPermissionRequestException();
        }
    }
}
