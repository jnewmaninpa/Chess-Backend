package chess.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Username Already Exists")
public class UsernameAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -2863882337697584815L;

	public UsernameAlreadyExistsException(String msg) {
		super(msg);
	}

}
