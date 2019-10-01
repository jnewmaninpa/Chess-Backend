package chess.ai;

public class GameOverException extends RuntimeException {

	private static final long serialVersionUID = -2738882945538970896L;

	public GameOverException(String msg) {
		super(msg);
	}

}
