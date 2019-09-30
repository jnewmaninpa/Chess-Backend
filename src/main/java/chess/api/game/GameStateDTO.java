package chess.api.game;

import chess.piece.properties.PieceColor;

public class GameStateDTO {

	private PieceColor activePlayer;
	private PieceColor playerInCheck;
	private Boolean gameOver;

	public GameStateDTO(PieceColor activePlayer, PieceColor playerInCheck, Boolean gameOver) {
		this.activePlayer = activePlayer;
		this.playerInCheck = playerInCheck;
		this.gameOver = gameOver;
	}

	public PieceColor getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(PieceColor activePlayer) {
		this.activePlayer = activePlayer;
	}

	public PieceColor getPlayerInCheck() {
		return playerInCheck;
	}

	public void setPlayerInCheck(PieceColor playerInCheck) {
		this.playerInCheck = playerInCheck;
	}

	public Boolean getGameOver() {
		return gameOver;
	}

	public void setGameOver(Boolean gameOver) {
		this.gameOver = gameOver;
	}
}
