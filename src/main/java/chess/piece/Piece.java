package chess.piece;

import java.util.List;
import java.util.stream.Collectors;

import chess.game.move.Move;
import chess.gamestate.GameState;
import chess.piece.properties.PieceColor;
import chess.piece.properties.PieceType;
import chess.position.Position;

public abstract class Piece {

	protected Position position;
	protected final PieceColor color;
	protected final PieceType type;
	protected final char boardRepresentation;

	// Constructor
	public Piece(char file, int rank, PieceColor color) {
		this.position = new Position(file, rank);
		this.color = color;
		if (this instanceof Bishop) {
			this.type = PieceType.BISHOP;
			this.boardRepresentation = (color == PieceColor.WHITE) ? 'B' : 'b';
		} else if (this instanceof King) {
			this.type = PieceType.KING;
			this.boardRepresentation = (color == PieceColor.WHITE) ? 'K' : 'k';
		} else if (this instanceof Knight) {
			this.type = PieceType.KNIGHT;
			this.boardRepresentation = (color == PieceColor.WHITE) ? 'N' : 'n';
		} else if (this instanceof Pawn) {
			this.type = PieceType.PAWN;
			this.boardRepresentation = (color == PieceColor.WHITE) ? 'P' : 'p';
		} else if (this instanceof Queen) {
			this.type = PieceType.QUEEN;
			this.boardRepresentation = (color == PieceColor.WHITE) ? 'Q' : 'q';
		} else if (this instanceof Rook) {
			this.type = PieceType.ROOK;
			this.boardRepresentation = (color == PieceColor.WHITE) ? 'R' : 'r';
		} else {
			this.type = PieceType.NONE;
			this.boardRepresentation = '-';
		}
	}

	public boolean moveValid(GameState gameState, Position position) {
		List<Move> legalMoves = this.getLegalMoves(gameState);

		if (this.color == gameState.getActiveColor()) {
			for (Move move : legalMoves) {
				if (move.getPosition().equals(position)) {
					return true;
				}
			}
		}
		return false;
	}

	// Getters
	public Position getPosition() {
		return position;
	}

	public PieceColor getColor() {
		return color;
	}

	public PieceType getType() {
		return type;
	}

	public char getRepresentation() {
		return boardRepresentation;
	}

	// Setters
	public void setPosition(Position position) {
		this.position = position;
	}

	protected King getMyKing(GameState gameState) {
		List<Piece> king = gameState.getPieceList().stream().filter(piece -> piece.getColor() == this.color)
				.filter(piece -> piece.getType() == PieceType.KING).collect(Collectors.toList());

		if (king.size() == 1) {
			return (King) king.get(0);
		}

		assert king.size() == 1 : "There must be exactly one King of each color";
		return null;
	}

	public boolean myKingInCheck(GameState gameState) {
		return getMyKing(gameState).check(gameState);
	}

	public boolean myKingInDoubleCheck(GameState gameState) {
		return getMyKing(gameState).doubleCheck(gameState);
	}

	public abstract List<Position> getKingDangerSquares(GameState gameState);

	public abstract List<Move> getPseudoLegalMoves(GameState gameState);

	public abstract List<Move> getLegalMoves(GameState gameState);

	@Override
	public String toString() {
		return "" + boardRepresentation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Piece)) {
			return false;
		}
		Piece other = (Piece) obj;
		if (color != other.color) {
			return false;
		}
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		return type == other.type;
	}
}
