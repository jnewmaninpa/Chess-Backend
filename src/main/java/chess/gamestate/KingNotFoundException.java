package chess.gamestate;

public class KingNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6033840307979997077L;
	
	public KingNotFoundException(String msg) {
		super(msg);
	}

}
