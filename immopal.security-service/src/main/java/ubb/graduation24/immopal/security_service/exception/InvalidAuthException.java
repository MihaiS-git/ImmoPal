package ubb.graduation24.immopal.security_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class InvalidAuthException extends RuntimeException {
    public InvalidAuthException() {
        super("Invalid username or password");
    }
}
