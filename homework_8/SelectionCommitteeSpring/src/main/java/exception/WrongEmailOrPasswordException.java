package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//check please, why it won`t work without it
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongEmailOrPasswordException extends RuntimeException{
    public WrongEmailOrPasswordException(String message) {
        super(message);
    }

    public WrongEmailOrPasswordException() {
    }
}
