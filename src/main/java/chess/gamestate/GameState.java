package chess.gamestate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import chess.position.Position;
import chess.game.move.Move;
import chess.piece.*;
import chess.piece.properties.PieceColor;
import chess.piece.properties.PieceType;

public class GameState {

	public static class Builder {
		private PieceColor activeColor;
		private List<Piece> pieceList;
		private CastlingAvailability castlingAvailability;
		private Position enPassantTargetSquare;
		private int halfmoveClock;
		private int fullmoveNumber;

		public Builder withActiveColor(PieceColor activeColor) {
			this.activeColor = activeColor;
			return this;
		}

		public Builder withPieceList(List<Piece> pieceList) {
			this.pieceList = pieceList;
			return this;
		}

		public Builder withCastlingAvailability(CastlingAvailability castlingAvailability) {
			this.castlingAvailability = castlingAvailability;
			return this;
		}

		public Builder withEnPassantTargetSquare(Position enPassantTargetSquare) {
			this.enPassantTargetSquare = enPassantTargetSquare;
			return this;
		}

		public Builder withHalfmoveClock(int halfmoveClock) {
			this.halfmoveClock = halfmoveClock;
			return this;
		}

		public Builder withFullmoveNumber(int fullmoveNumber) {
			this.fullmoveNumber = fullmoveNumber;
			return this;
		}

		public GameState build() {
			GameState gameState = new GameState();
			gameState.activeColor = this.activeColor;
			gameState.pieceList = this.pieceList;
			gameState.castlingAvailability = this.castlingAvailability;
			gameState.enPassantTargetSquare = this.enPassantTargetSquare;
			gameState.halfmoveClock = this.halfmoveClock;
			gameState.fullmoveNumber = this.fullmoveNumber;
			return gameState;
		}
	}

	private PieceColor activeColor;
	private List<Piece> pieceList;
	private CastlingAvailability castlingAvailability;
	private Position enPassantTargetSquare;
	private int fullmoveNumber;
	private int halfmoveClock;

	public GameState copy() {
		return new GameState.Builder().withActiveColor(this.activeColor).withPieceList(this.copyOfPieceList())
				.withCastlingAvailability(this.copyOfCastlingAvailability())
				.withEnPassantTargetSquare(this.copyOfEnPassantTargetSquare()).withHalfmoveClock(this.fullmoveNumber)
				.withFullmoveNumber(this.halfmoveClock).build();
	}

	private CastlingAvailability copyOfCastlingAvailability() {
		return new CastlingAvailability.Builder().withBlackKingside(this.castlingAvailability.getBlackKingside())
				.withBlackQueenside(this.castlingAvailability.getBlackQueenside())
				.withWhiteKingside(this.castlingAvailability.getWhiteKingside())
				.withWhiteQueenside(this.castlingAvailability.getWhiteQueenside()).build();
	}

	private Position copyOfEnPassantTargetSquare() {
		if (this.enPassantTargetSquare != null)
			return new Position(this.enPassantTargetSquare.getRank(), this.enPassantTargetSquare.getFile());
		else
			return null;
	}

	private List<Piece> copyOfPieceList() {
		List<Piece> copy = new ArrayList<>();
		for (Piece piece : pieceList) {
			if (piece instanceof Bishop) {
				copy.add(new Bishop(piece.getPosition().getFile(), piece.getPosition().getRank(), piece.getColor()));
			} else if (piece instanceof King) {
				copy.add(new King(piece.getPosition().getFile(), piece.getPosition().getRank(), piece.getColor()));
			} else if (piece instanceof Knight) {
				copy.add(new Knight(piece.getPosition().getFile(), piece.getPosition().getRank(), piece.getColor()));
			} else if (piece instanceof Pawn) {
				copy.add(new Pawn(piece.getPosition().getFile(), piece.getPosition().getRank(), piece.getColor()));
			} else if (piece instanceof Queen) {
				copy.add(new Queen(piece.getPosition().getFile(), piece.getPosition().getRank(), piece.getColor()));
			} else if (piece instanceof Rook) {
				copy.add(new Rook(piece.getPosition().getFile(), piece.getPosition().getRank(), piece.getColor()));
			}
		}
		return copy;
	}

	private GameState() {
	}

	public void addMove(Move move) {
		// update color
		if (activeColor == PieceColor.WHITE) {
			activeColor = PieceColor.BLACK;
		} else {
			activeColor = PieceColor.WHITE;
			// if it is black's turn add a full move
			fullmoveNumber++;
		}
		// add a half move
		halfmoveClock++; // TODO actually have this do what it is meant for.....

		// TODO update castlingAvaiability & enPassantTargetSquare
		this.enPassantTargetSquare = null;
		if (move.getPiece().getType() == PieceType.PAWN) {
			if (move.getPieceInitialPosition().getY() - move.getPosition().getY() == 2) {
				this.enPassantTargetSquare = new Position(move.getPieceInitialPosition().getX(),
						move.getPieceInitialPosition().getY() - 1);
			}
			if (move.getPieceInitialPosition().getY() - move.getPosition().getY() == -2) {
				this.enPassantTargetSquare = new Position(move.getPieceInitialPosition().getX(),
						move.getPieceInitialPosition().getY() + 1);
			}
		}
	}

	public PieceColor getActiveColor() {
		return activeColor;
	}

	public List<Piece> getPieceList() {
		return pieceList;
	}

	public Board getBoard() {
		return new Board(this);
	}

	public boolean whiteInCheck(GameState gameState) {
		Optional<Piece> matchingPiece = pieceList.stream().filter(piece -> piece.getColor() == PieceColor.WHITE)
				.filter(piece -> piece.getType() == PieceType.KING).findFirst();
		
		if (!matchingPiece.isPresent()) {
			throw new KingNotFoundException("The white king could not be found");
		}

		King king = (King) matchingPiece.get();

		return king.check(gameState);
	}

	public boolean blackInCheck(GameState gameState) {
		Optional<Piece> matchingPiece = pieceList.stream().filter(piece -> piece.getColor() == PieceColor.BLACK)
				.filter(piece -> piece.getType() == PieceType.KING).findFirst();
		
		if (!matchingPiece.isPresent()) {
			throw new KingNotFoundException("The black king could not be found");
		}

		King king = (King) matchingPiece.get();

		return king.check(gameState);
	}

	public boolean playerInCheck() {
		List<Piece> kings = pieceList.stream().filter(piece -> piece.getType() == PieceType.KING)
				.collect(Collectors.toList());

		for (Piece king : kings) {
			if (((King) king).check(this)) {
				return true;
			}
		}
		return false;
	}

	public Piece getPieceAt(Position position) {

		for (Piece piece : pieceList) {
			if (piece.getPosition().equals(position)) {
				return piece;
			}
		}

		return new None(position.getFile(), position.getRank());
	}

	public List<Move> getAllMoves() {
		List<Move> allPossibleMoves = new ArrayList<>();

		List<Piece> playerPieces = pieceList.stream().filter(piece -> piece.getColor() == activeColor)
				.collect(Collectors.toList());

		for (Piece piece : playerPieces) {
			allPossibleMoves.addAll(piece.getLegalMoves(this));
		}

		return allPossibleMoves;
	}

	public PieceColor getPlayerInCheck() {

		if (whiteInCheck(this)) {
			return PieceColor.WHITE;
		} else if (blackInCheck(this)) {
			return PieceColor.BLACK;
		} else {
			return PieceColor.NONE;
		}
	}

	public boolean getGameOver() {
		return getAllMoves().isEmpty() || pieceList.size() <= 2;
	}

	public CastlingAvailability getCastlingAvailability() {
		return castlingAvailability;
	}

	public Position getEnPassantTargetSquare() {
		return enPassantTargetSquare;
	}

	public void setEnPassantTargetSquare(Position pos) {
		this.enPassantTargetSquare = pos;
	}

	public int getFullmoveNumber() {
		return fullmoveNumber;
	}

	public int getHalfmoveClock() {
		return halfmoveClock;
	}
}