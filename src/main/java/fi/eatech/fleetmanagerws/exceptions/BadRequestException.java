package fi.eatech.fleetmanagerws.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RuntimeException which return 400 httpstatus when throw it
 *
 * @author Ville Nupponen
 * @since 1.0.0
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String s) {
        super(s);
    }
}
