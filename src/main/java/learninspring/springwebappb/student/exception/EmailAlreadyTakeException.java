package learninspring.springwebappb.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This email was already taken")
public class EmailAlreadyTakeException extends RuntimeException {

    public EmailAlreadyTakeException(String message) {
        super(message);
    }
}
