package chess.api.exceptions;

public class MoveNotValidException extends Exception {

	private static final long serialVersionUID = -7505571577847957192L;

	public MoveNotValidException(String msg) {
		super(msg);
	}

}
