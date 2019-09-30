package chess.piece;

import java.util.List;

import chess.game.move.Move;
import chess.gameState.GameState;
import chess.move.logic.AttackedPositions;
import chess.move.logic.Legal;
import chess.move.logic.PsuedoLegal;
import chess.piece.properties.PieceColor;
import chess.position.Position;

public final class Rook extends Piece {

	public Rook(char file, int rank, PieceColor color) {
		super(file, rank, color);
	}

	@Override
	public List<Position> getKingDangerSquares(GameState gameState) {
		return AttackedPositions.Rook(this, gameState);
	}

	@Override
	public List<Move> getPseudoLegalMoves(GameState gameState) {
		return PsuedoLegal.Rook(this, gameState);
	}

	@Override
	public List<Move> getLegalMoves(GameState gameState) {
		return Legal.Rook(this, gameState);
	}
}