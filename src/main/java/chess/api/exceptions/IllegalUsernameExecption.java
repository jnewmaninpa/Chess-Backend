package chess.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal character in username")
public class IllegalUsernameExecption extends Exception {

	private static final long serialVersionUID = -1313126964902937858L;

	public IllegalUsernameExecption(String msg) {
		super(msg);
	}

}
