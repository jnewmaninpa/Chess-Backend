package chess.move.logic;

import java.util.ArrayList;
import java.util.List;

import chess.position.Position;
import chess.game.move.Move;
import chess.game.move.PawnUpgradeMove;
import chess.gameState.GameState;
import chess.piece.Piece;
import chess.piece.properties.PieceColor;
import chess.piece.properties.PieceType;

public class PsuedoLegal {

	public static List<Move> Rook(Piece piece, GameState gameState) {

		List<Move> plpl = new ArrayList<>();

		Position piecePos = piece.getPosition();

		// check for legal moves up (+y)
		for (int y = piecePos.getY() + 1; y < 8; y++) {

			Piece tempPiece = gameState.getPieceAt(new Position(piecePos.getX(), y));
			if (tempPiece.getColor() != piece.getColor()) {
				plpl.add(new Move(piece, tempPiece.getPosition()));
			}
			if (tempPiece.getType() != PieceType.NONE) {
				break;
			}
		}

		// check for legal moves up (-y)
		for (int y = piecePos.getY() - 1; y >= 0; y--) {

			Piece tempPiece = gameState.getPieceAt(new Position(piecePos.getX(), y));
			if (tempPiece.getColor() != piece.getColor()) {
				plpl.add(new Move(piece, tempPiece.getPosition()));
			}
			if (tempPiece.getType() != PieceType.NONE) {
				break;
			}
		}

		// check for legal moves up (+x)
		for (int x = piecePos.getX() + 1; x < 8; x++) {

			Piece tempPiece = gameState.getPieceAt(new Position(x, piecePos.getY()));
			if (tempPiece.getColor() != piece.getColor()) {
				plpl.add(new Move(piece, tempPiece.getPosition()));
			}
			if (tempPiece.getType() != PieceType.NONE) {
				break;
			}
		}

		// check for legal moves up (-x)
		for (int x = piecePos.getX() - 1; x >= 0; x--) {

			Piece tempPiece = gameState.getPieceAt(new Position(x, piecePos.getY()));
			if (tempPiece.getColor() != piece.getColor()) {
				plpl.add(new Move(piece, tempPiece.getPosition()));
			}
			if (tempPiece.getType() != PieceType.NONE) {
				break;
			}
		}

		return plpl;
	}

	public static List<Move> Bishop(Piece piece, GameState gameState) {
		List<Move> plpl = new ArrayList<>();

		Position piecePos = piece.getPosition();

		// check for legal moves up, right (+y, +x)
		for (int y = 1; y < 8; y++) {
			final int staticY = piecePos.getY() + y;
			final int staticX = piecePos.getX() + y;
			if (staticX > 7 || staticY > 7) {
				break;
			}
			Piece tempPiece = gameState.getPieceAt(new Position(staticX, staticY));
			if (tempPiece.getColor() != piece.getColor())
				plpl.add(new Move(piece, tempPiece.getPosition()));
			if (tempPiece.getType() != PieceType.NONE) {
				break;
			}
		}

		// check for legal moves down, left (-y, -x)
		for (int y = 1; y < 8; y++) {
			final int staticY = piecePos.getY() - y;
			final int staticX = piecePos.getX() - y;
			if (staticX < 0 || staticY < 0) {
				break;
			}
			Piece tempPiece = gameState.getPieceAt(new Position(staticX, staticY));
			if (tempPiece.getColor() != piece.getColor())
				plpl.add(new Move(piece, tempPiece.getPosition()));
			if (tempPiece.getType() != PieceType.NONE) {
				break;
			}
		}

		// check for legal moves up, left (+y, -x)
		for (int y = 1; y < 8; y++) {
			final int staticY = piecePos.getY() + y;
			final int staticX = piecePos.getX() - y;
			if (staticX < 0 || staticY > 7) {
				break;
			}
			Piece tempPiece = gameState.getPieceAt(new Position(staticX, staticY));
			if (tempPiece.getColor() != piece.getColor())
				plpl.add(new Move(piece, tempPiece.getPosition()));
			if (tempPiece.getType() != PieceType.NONE) {
				break;
			}
		}

		// check for legal moves down, right (-y, +x)
		for (int y = 1; y < 8; y++) {
			final int staticY = piecePos.getY() - y;
			final int staticX = piecePos.getX() + y;
			if (staticX > 7 || staticY < 0) {
				break;
			}
			Piece tempPiece = gameState.getPieceAt(new Position(staticX, staticY));
			if (tempPiece.getColor() != piece.getColor())
				plpl.add(new Move(piece, tempPiece.getPosition()));
			if (tempPiece.getType() != PieceType.NONE) {
				break;
			}
		}

		return plpl;
	}

	public static List<Move> Queen(Piece piece, GameState gameState) {

		List<Move> plpl = new ArrayList<>();

		plpl.addAll(PsuedoLegal.Rook(piece, gameState));
		plpl.addAll(PsuedoLegal.Bishop(piece, gameState));

		return plpl;
	}

	public static List<Move> Knight(Piece piece, GameState gameState) {
		List<Move> plpl = new ArrayList<>();

		Position piecePos = piece.getPosition();
		int x = piecePos.getX();
		int y = piecePos.getY();

		List<Position> positionList = new ArrayList<>();
		if (x < 7 && y < 6)
			positionList.add(new Position(x + 1, y + 2)); // up right
		if (x > 0 && y < 6)
			positionList.add(new Position(x - 1, y + 2)); // up left
		if (x < 7 && y > 1)
			positionList.add(new Position(x + 1, y - 2)); // down right
		if (x > 0 && y > 1)
			positionList.add(new Position(x - 1, y - 2)); // down left
		if (x < 6 && y < 7)
			positionList.add(new Position(x + 2, y + 1)); // right up
		if (x < 6 && y > 0)
			positionList.add(new Position(x + 2, y - 1)); // right down
		if (x > 1 && y < 7)
			positionList.add(new Position(x - 2, y + 1)); // left up
		if (x > 1 && y > 0)
			positionList.add(new Position(x - 2, y - 1)); // left down

		for (Position position : positionList) {
			Piece tempPiece = gameState.getPieceAt(position);
			if (tempPiece.getColor() != piece.getColor()) {
				plpl.add(new Move(piece, tempPiece.getPosition()));
			}
		}

		return plpl;
	}

	public static List<Move> King(Piece piece, GameState gameState) {
		List<Move> plpl = new ArrayList<>();

		Position piecePos = piece.getPosition();
		int x = piecePos.getX();
		int y = piecePos.getY();

		List<Position> positionList = new ArrayList<>();
		if (x < 7 && y < 7)
			positionList.add(new Position(x + 1, y + 1)); // up right
		if (x > 0 && y < 7)
			positionList.add(new Position(x - 1, y + 1)); // up left
		if (x < 7 && y > 0)
			positionList.add(new Position(x + 1, y - 1)); // down right
		if (x > 0 && y > 0)
			positionList.add(new Position(x - 1, y - 1)); // down left
		if (y < 7)
			positionList.add(new Position(x, y + 1)); // up
		if (x > 0)
			positionList.add(new Position(x - 1, y)); // left
		if (x < 7)
			positionList.add(new Position(x + 1, y)); // right
		if (y > 0)
			positionList.add(new Position(x, y - 1)); // down

		for (Position position : positionList) {
			Piece tempPiece = gameState.getPieceAt(position);
			if (tempPiece.getColor() != piece.getColor()) {
				plpl.add(new Move(piece, tempPiece.getPosition()));
			}
		}

		return plpl;
	}

	public static List<Move> Pawn(Piece piece, GameState gameState) {
		List<Move> plpl = new ArrayList<>();

		Position piecePos = piece.getPosition();

		if (piece.getColor() == PieceColor.WHITE) { // white pawn

			// no pawn upgrade
			if (piecePos.getY() != 6) {

				// normal move
				Piece tempPiece1 = gameState.getPieceAt(new Position(piecePos.getX(), piecePos.getY() + 1));
				if (tempPiece1.getType() == PieceType.NONE) {
					plpl.add(new Move(piece, tempPiece1.getPosition()));
				}

				// first move
				if (piecePos.getY() == 1) {
					Piece tempPiece2 = gameState.getPieceAt(new Position(piecePos.getX(), piecePos.getY() + 2));
					if ((tempPiece1.getType() == PieceType.NONE) && (tempPiece2.getType() == PieceType.NONE)) {
						plpl.add(new Move(piece, tempPiece2.getPosition()));
					}
				}

				// take piece left
				Piece tempPiece3 = gameState.getPieceAt(new Position(piecePos.getX() - 1, piecePos.getY() + 1));
				if (tempPiece3.getType() != PieceType.NONE && tempPiece3.getColor() != piece.getColor()) {
					plpl.add(new Move(piece, tempPiece3.getPosition()));
				}

				// take piece right
				Piece tempPiece4 = gameState.getPieceAt(new Position(piecePos.getX() + 1, piecePos.getY() + 1));
				if (tempPiece4.getType() != PieceType.NONE && tempPiece4.getColor() != piece.getColor()) {
					plpl.add(new Move(piece, tempPiece4.getPosition()));
				}

				// pawn upgrade
			} else { // if (piecePos.getY() == 6)

				Piece tempPiece1 = gameState.getPieceAt(new Position(piecePos.getX(), piecePos.getY() + 1));
				if (tempPiece1.getType() == PieceType.NONE) {
					plpl.add(new PawnUpgradeMove(piece, tempPiece1.getPosition(), PieceType.QUEEN));
					plpl.add(new PawnUpgradeMove(piece, tempPiece1.getPosition(), PieceType.ROOK));
					plpl.add(new PawnUpgradeMove(piece, tempPiece1.getPosition(), PieceType.BISHOP));
					plpl.add(new PawnUpgradeMove(piece, tempPiece1.getPosition(), PieceType.KNIGHT));
				}

				// take piece left
				Piece tempPiece3 = gameState.getPieceAt(new Position(piecePos.getX() - 1, piecePos.getY() + 1));
				if (tempPiece3.getType() != PieceType.NONE && tempPiece3.getColor() != piece.getColor()) {
					plpl.add(new PawnUpgradeMove(piece, tempPiece3.getPosition(), PieceType.QUEEN));
					plpl.add(new PawnUpgradeMove(piece, tempPiece3.getPosition(), PieceType.ROOK));
					plpl.add(new PawnUpgradeMove(piece, tempPiece3.getPosition(), PieceType.BISHOP));
					plpl.add(new PawnUpgradeMove(piece, tempPiece3.getPosition(), PieceType.KNIGHT));
				}

				// take piece right
				Piece tempPiece4 = gameState.getPieceAt(new Position(piecePos.getX() + 1, piecePos.getY() + 1));
				if (tempPiece4.getType() != PieceType.NONE && tempPiece4.getColor() != piece.getColor()) {
					plpl.add(new PawnUpgradeMove(piece, tempPiece4.getPosition(), PieceType.QUEEN));
					plpl.add(new PawnUpgradeMove(piece, tempPiece4.getPosition(), PieceType.ROOK));
					plpl.add(new PawnUpgradeMove(piece, tempPiece4.getPosition(), PieceType.BISHOP));
					plpl.add(new PawnUpgradeMove(piece, tempPiece4.getPosition(), PieceType.KNIGHT));
				}

			}

		} else if (piece.getColor() == PieceColor.BLACK) { // black pawn

			// no pawn upgrade
			if (piecePos.getY() != 1) {

				// normal move
				Piece tempPiece1 = gameState.getPieceAt(new Position(piecePos.getX(), piecePos.getY() - 1));
				if (tempPiece1.getType() == PieceType.NONE) {
					plpl.add(new Move(piece, tempPiece1.getPosition()));
				}

				// first move
				if (piecePos.getY() == 6) {
					Piece tempPiece2 = gameState.getPieceAt(new Position(piecePos.getX(), piecePos.getY() - 2));
					if ((tempPiece1.getType() == PieceType.NONE) && (tempPiece2.getType() == PieceType.NONE)) {
						plpl.add(new Move(piece, tempPiece2.getPosition()));
					}
				}

				// take piece left
				Piece tempPiece3 = gameState.getPieceAt(new Position(piecePos.getX() - 1, piecePos.getY() - 1));
				if (tempPiece3.getType() != PieceType.NONE && tempPiece3.getColor() != piece.getColor()) {
					plpl.add(new Move(piece, tempPiece3.getPosition()));
				}

				// take piece right
				Piece tempPiece4 = gameState.getPieceAt(new Position(piecePos.getX() + 1, piecePos.getY() - 1));
				if (tempPiece4.getType() != PieceType.NONE && tempPiece4.getColor() != piece.getColor()) {
					plpl.add(new Move(piece, tempPiece4.getPosition()));
				}

				// pawn upgrade
			} else { // (piecePos.getY() == 1)

				// normal move
				Piece tempPiece1 = gameState.getPieceAt(new Position(piecePos.getX(), piecePos.getY() - 1));
				if (tempPiece1.getType() == PieceType.NONE) {
					plpl.add(new PawnUpgradeMove(piece, tempPiece1.getPosition(), PieceType.QUEEN));
					plpl.add(new PawnUpgradeMove(piece, tempPiece1.getPosition(), PieceType.ROOK));
					plpl.add(new PawnUpgradeMove(piece, tempPiece1.getPosition(), PieceType.BISHOP));
					plpl.add(new PawnUpgradeMove(piece, tempPiece1.getPosition(), PieceType.KNIGHT));
				}

				// take piece left
				Piece tempPiece3 = gameState.getPieceAt(new Position(piecePos.getX() - 1, piecePos.getY() - 1));
				if (tempPiece3.getType() != PieceType.NONE && tempPiece3.getColor() != piece.getColor()) {
					plpl.add(new PawnUpgradeMove(piece, tempPiece3.getPosition(), PieceType.QUEEN));
					plpl.add(new PawnUpgradeMove(piece, tempPiece3.getPosition(), PieceType.ROOK));
					plpl.add(new PawnUpgradeMove(piece, tempPiece3.getPosition(), PieceType.BISHOP));
					plpl.add(new PawnUpgradeMove(piece, tempPiece3.getPosition(), PieceType.KNIGHT));
				}

				// take piece right
				Piece tempPiece4 = gameState.getPieceAt(new Position(piecePos.getX() + 1, piecePos.getY() - 1));
				if (tempPiece4.getType() != PieceType.NONE && tempPiece4.getColor() != piece.getColor()) {
					plpl.add(new PawnUpgradeMove(piece, tempPiece4.getPosition(), PieceType.QUEEN));
					plpl.add(new PawnUpgradeMove(piece, tempPiece4.getPosition(), PieceType.ROOK));
					plpl.add(new PawnUpgradeMove(piece, tempPiece4.getPosition(), PieceType.BISHOP));
					plpl.add(new PawnUpgradeMove(piece, tempPiece4.getPosition(), PieceType.KNIGHT));
				}

			}
		}

		// Also add the en passant square if applicable
		if (gameState.getEnPassantTargetSquare() != null) {
			if ((gameState.getEnPassantTargetSquare().getX() == (piecePos.getX() + 1))
					|| (gameState.getEnPassantTargetSquare().getX() == (piecePos.getX() - 1))) {
				if (((gameState.getEnPassantTargetSquare().getY() == (piecePos.getY() + 1))
						&& piece.getColor() == PieceColor.WHITE)
						|| ((gameState.getEnPassantTargetSquare().getY() == (piecePos.getY() - 1))
								&& piece.getColor() == PieceColor.BLACK)) {
					plpl.add(new Move(piece, gameState.getEnPassantTargetSquare()));
				}
			}
		}

		return plpl;
	}

}
