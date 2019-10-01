package chess.piece;

import java.util.ArrayList;
import java.util.List;

import chess.game.move.Move;
import chess.gamestate.GameState;
import chess.move.logic.AttackedPositions;
import chess.move.logic.Legal;
import chess.move.logic.PsuedoLegal;
import chess.piece.properties.PieceColor;
import chess.position.Position;

public final class King extends Piece {

	public King(char file, int rank, PieceColor color) {
		super(file, rank, color);
	}

	public List<Piece> getAttackers(GameState gameState) {
		List<Piece> attackersList = new ArrayList<>();

		for (Piece piece : gameState.getPieceList()) {
			if (piece.getColor() != this.color) {
				for (Move move : piece.getPseudoLegalMoves(gameState)) {
					if (move.getPosition().equals(this.position)) {
						attackersList.add(piece);
						break;
					}
				}
			}
		}
		return attackersList;
	}

	// Check if there are any pieces in the attackers array
	public boolean check(GameState gameState) {
		return !getAttackers(gameState).isEmpty();
	}

	public boolean doubleCheck(GameState gameState) {
		return getAttackers(gameState).size() > 1;
	}

	@Override
	public List<Position> getKingDangerSquares(GameState gameState) {
		return AttackedPositions.king(this);
	}

	@Override
	public List<Move> getPseudoLegalMoves(GameState gameState) {
		return PsuedoLegal.king(this, gameState);
	}

	@Override
	public List<Move> getLegalMoves(GameState gameState) {
		return Legal.king(this, gameState);
	}
}
