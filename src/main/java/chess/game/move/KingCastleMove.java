package chess.game.move;

import chess.gamestate.GameState;
import chess.piece.Piece;
import chess.piece.properties.PieceColor;
import chess.position.Position;

public class KingCastleMove extends Move {

	public KingCastleMove(Piece piece, Position position) {
		super(piece, position);
	}

	@Override
	public void move(GameState gameState) {

		// the move is a castle
		if (piece.getColor() == PieceColor.WHITE) {
			if (position.getX() == 2) {
				piece.setPosition(new Position(2, 0));
				gameState.getPieceAt(new Position('a', 1)).setPosition(new Position(3, 0));
				gameState.getCastlingAvailability().setWhiteKingside(false);
				gameState.getCastlingAvailability().setWhiteQueenside(false);
			} else if (position.getX() == 6) {
				piece.setPosition(new Position(6, 0));
				gameState.getPieceAt(new Position('h', 1)).setPosition(new Position(5, 0));
				gameState.getCastlingAvailability().setWhiteKingside(false);
				gameState.getCastlingAvailability().setWhiteQueenside(false);
			}
		} else { // color is BLACK
			if (position.getX() == 2) {
				piece.setPosition(new Position(2, 7));
				gameState.getPieceAt(new Position('a', 8)).setPosition(new Position(3, 7));
				gameState.getCastlingAvailability().setBlackKingside(false);
				gameState.getCastlingAvailability().setBlackQueenside(false);
			} else if (position.getX() == 6) {
				piece.setPosition(new Position(6, 7));
				gameState.getPieceAt(new Position('h', 8)).setPosition(new Position(5, 7));
				gameState.getCastlingAvailability().setBlackKingside(false);
				gameState.getCastlingAvailability().setBlackQueenside(false);
			}
		}
		gameState.addMove(this);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
