package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PasswordDontMatchException extends RuntimeException {
    public PasswordDontMatchException() {
    }

    public PasswordDontMatchException(String message) {
        super(message);
    }
}
