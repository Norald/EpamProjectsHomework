package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SuchEmailExistException extends RuntimeException{
    public SuchEmailExistException() {
    }

    public SuchEmailExistException(String message) {
        super(message);
    }
}
