package chess.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Game Does Not Exist")
public class GameDoesNotExistException extends Exception {

	private static final long serialVersionUID = 1016284462663859533L;

	public GameDoesNotExistException(String msg) {
		super(msg);
	}
}