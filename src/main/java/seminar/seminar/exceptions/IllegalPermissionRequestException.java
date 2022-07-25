package seminar.seminar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class IllegalPermissionRequestException extends RuntimeException {
    public IllegalPermissionRequestException() {
        super("잘못된 권한 요청입니다.");
    }
}
