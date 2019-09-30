package chess.game.move;

import chess.gameState.FEN;
import chess.gameState.GameState;
import chess.piece.King;
import chess.piece.Piece;
import chess.piece.Rook;
import chess.piece.properties.PieceColor;
import chess.piece.properties.PieceType;
import chess.position.Position;

public class Move {

	protected final Piece piece;
	protected final Position pieceInitialPosition;
	protected final Position position;
	protected String fenBeforeMove;
	protected Piece removedPiece;

	public Move(Piece piece, Position position) {
		this.piece = piece;
		this.pieceInitialPosition = piece.getPosition();
		this.position = position;
	}

	public Piece getPiece() {
		return piece;
	}

	public Position getPieceInitialPosition() {
		return pieceInitialPosition;
	}

	public Position getPosition() {
		return position;
	}

	public Piece getRemovedPiece() {
		return removedPiece;
	}

	public String getFenBeforeMove() {
		return fenBeforeMove;
	}

	@Override
	public String toString() {
		return pieceInitialPosition + "" + position;
	}

	public void move(GameState gameState) {
		fenBeforeMove = FEN.getFen(gameState);
		// remove castling if applicable
		if (piece instanceof Rook) {
			if (this.position.getX() == 0) {
				if (piece.getColor() == PieceColor.WHITE) {
					gameState.getCastlingAvailability().setWhiteQueenside(false);
				} else {
					gameState.getCastlingAvailability().setBlackQueenside(false);
				}
			} else if (this.position.getX() == 7) {
				if (piece.getColor() == PieceColor.WHITE) {
					gameState.getCastlingAvailability().setWhiteKingside(false);
				} else {
					gameState.getCastlingAvailability().setBlackKingside(false);
				}
			}
		}
		if (piece instanceof King) {
			if (piece.getColor() == PieceColor.WHITE) {
				gameState.getCastlingAvailability().setWhiteKingside(false);
				gameState.getCastlingAvailability().setWhiteQueenside(false);
			} else {
				gameState.getCastlingAvailability().setBlackKingside(false);
				gameState.getCastlingAvailability().setBlackQueenside(false);
			}
		}

		removedPiece = gameState.getPieceAt(position);
		gameState.getPieceList().remove(gameState.getPieceAt(position));
		piece.setPosition(position);

		// check if the move was an en passant
		// if it was remove the piece that was taken
		if (piece.getType() == PieceType.PAWN && position.equals(gameState.getEnPassantTargetSquare())) {
			if (gameState.getPieceAt(position).getColor() == PieceColor.WHITE) {
				gameState.getPieceList()
						.remove(gameState.getPieceAt(new Position(position.getX(), position.getY() - 1)));
			} else {
				gameState.getPieceList()
						.remove(gameState.getPieceAt(new Position(position.getX(), position.getY() + 1)));
			}
		}

		gameState.addMove(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((piece == null) ? 0 : piece.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (piece == null) {
			if (other.piece != null)
				return false;
		} else if (!piece.equals(other.piece))
			return false;
		if (position == null) {
			return other.position == null;
		} else
			return position.equals(other.position);
	}

}
