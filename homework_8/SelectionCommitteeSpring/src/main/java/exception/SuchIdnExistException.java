package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SuchIdnExistException extends RuntimeException {
    public SuchIdnExistException() {
    }

    public SuchIdnExistException(String message) {
        super(message);
    }
}
