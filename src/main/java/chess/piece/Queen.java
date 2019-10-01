package chess.piece;

import java.util.Arrays;
import java.util.List;

import chess.game.move.Move;
import chess.gamestate.GameState;
import chess.move.logic.AttackedPositions;
import chess.move.logic.Legal;
import chess.move.logic.PsuedoLegal;
import chess.piece.properties.PieceColor;
import chess.position.Position;

public final class Queen extends Piece {

	protected boolean[][] kingDangerSquares;

	public Queen(char file, int rank, PieceColor color) {
		super(file, rank, color);
	}

	@Override
	public List<Position> getKingDangerSquares(GameState gameState) {
		return AttackedPositions.queen(this, gameState);

	}

	@Override
	public List<Move> getPseudoLegalMoves(GameState gameState) {
		return PsuedoLegal.queen(this, gameState);
	}

	@Override
	public List<Move> getLegalMoves(GameState gameState) {
		return Legal.queen(this, gameState);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.deepHashCode(kingDangerSquares);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Queen other = (Queen) obj;
		if (!Arrays.deepEquals(kingDangerSquares, other.kingDangerSquares))
			return false;
		return true;
	}
	
}
