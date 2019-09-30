package chess.api.exceptions;

public class GameNotCreatedException extends Exception {

	private static final long serialVersionUID = 7202445043888536154L;

	public GameNotCreatedException(String msg) {
		super(msg);
	}
}
