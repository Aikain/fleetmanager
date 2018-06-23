package fi.eatech.fleetmanagerws.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RuntimeException which return 404 httpstatus when throw it
 *
 * @author Ville Nupponen
 * @since 0.0.1-SNAPSHOT
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String s) {
        super(s);
    }
}
