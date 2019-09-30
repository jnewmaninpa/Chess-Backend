package chess.api.exceptions;

public class NumberOfGamesNotFoundException extends Exception {

	private static final long serialVersionUID = 2019504569976614818L;

	public NumberOfGamesNotFoundException(String msg) {
		super(msg);
	}
}
