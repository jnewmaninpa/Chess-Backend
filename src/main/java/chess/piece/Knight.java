package chess.piece;

import java.util.List;

import chess.game.move.Move;
import chess.gameState.GameState;
import chess.move.logic.AttackedPositions;
import chess.move.logic.Legal;
import chess.move.logic.PsuedoLegal;
import chess.piece.properties.PieceColor;
import chess.position.Position;

public final class Knight extends Piece {

	public Knight(char file, int rank, PieceColor color) {
		super(file, rank, color);
	}

	@Override
	public List<Position> getKingDangerSquares(GameState gameState) {
		return AttackedPositions.Knight(this, gameState);
	}

	@Override
	public List<Move> getPseudoLegalMoves(GameState gameState) {
		return PsuedoLegal.Knight(this, gameState);
	}

	@Override
	public List<Move> getLegalMoves(GameState gameState) {
		return Legal.Knight(this, gameState);
	}
}
