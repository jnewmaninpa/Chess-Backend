package chess.piece;

import java.util.ArrayList;
import java.util.List;

import chess.game.move.Move;
import chess.gameState.GameState;
import chess.piece.properties.PieceColor;
import chess.position.Position;

public final class None extends Piece {

	public None(char file, int rank) {
		super(file, rank, PieceColor.NONE);
	}

	@Override
	public List<Move> getPseudoLegalMoves(GameState gameState) {
		// This returns an empty array
		return new ArrayList<Move>();
	}

	@Override
	public List<Move> getLegalMoves(GameState gameState) {
		// This returns an empty array
		return new ArrayList<Move>();
	}

	@Override
	public List<Position> getKingDangerSquares(GameState gameState) {
		return new ArrayList<Position>();
	}

}
