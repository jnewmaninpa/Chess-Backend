package chess.api.game;

import chess.gamestate.FEN;
import chess.gamestate.GameState;

public class GameModel {

	private final long id;
	private String fen;
	private GameState gameState;

	public GameModel(long id, String fen) {
		this.id = id;
		this.fen = fen;
		this.gameState = FEN.getGameState(fen);
	}

	public GameModel(String fen) {
		this.id = -1;
		this.fen = fen;
		this.gameState = FEN.getGameState(fen);
	}

	public long getId() {
		return id;
	}

	public String getFen() {
		return fen;
	}

	public void setFen(String fen) {
		this.fen = fen;
		// the gameState should always be tied to the fen
		this.gameState = FEN.getGameState(fen);
	}

	public GameStateDTO getGameState() {
		return new GameStateDTO(gameState.getActiveColor(), gameState.getPlayerInCheck(), gameState.getGameOver());
	}

}
