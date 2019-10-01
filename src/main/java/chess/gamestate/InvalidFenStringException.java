package chess.gamestate;

public class InvalidFenStringException extends RuntimeException {

	private static final long serialVersionUID = 5928242388863402496L;
	
	public InvalidFenStringException(String msg) {
		super(msg);
	}

}
