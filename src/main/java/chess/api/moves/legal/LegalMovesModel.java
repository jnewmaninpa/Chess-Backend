package chess.api.moves.legal;

import java.util.ArrayList;
import java.util.List;

import chess.api.game.GameModel;
import chess.game.move.Move;
import chess.gameState.FEN;
import chess.gameState.GameState;
import chess.piece.Piece;
import chess.position.Position;

public class LegalMovesModel {

	private final GameModel game;
	private final List<Move> legalMoves;

	public LegalMovesModel(GameModel game, Position position) {
		this.game = game;

		GameState gameState = FEN.getGameState(game.getFen());

		Piece piece = gameState.getPieceAt(position);

		if (gameState.getActiveColor() == piece.getColor())
			legalMoves = piece.getLegalMoves(gameState);
		else
			legalMoves = new ArrayList<>();

	}

	public GameModel getGame() {
		return game;
	}

	public List<Move> getLegalMoves() {
		return legalMoves;
	}

}
