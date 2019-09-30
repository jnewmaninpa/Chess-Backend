package chess.move.logic;

import java.util.ArrayList;
import java.util.List;

import chess.gameState.GameState;
import chess.piece.Piece;
import chess.piece.properties.PieceColor;
import chess.piece.properties.PieceType;
import chess.position.Position;

public class AttackedPositions {

	public static List<Position> Rook(Piece piece, GameState gameState) {
		List<Position> plpl = new ArrayList<>();

		Position piecePos = piece.getPosition();

		// check for legal moves up (+y)
		for (int y = piecePos.getY() + 1; y < 8; y++) {

			Piece tempPiece = gameState.getPieceAt(new Position(piecePos.getX(), y));
			plpl.add(tempPiece.getPosition());
			if (tempPiece.getType() != PieceType.NONE && tempPiece.getType() != PieceType.KING) {
				break;
			}
		}

		// check for legal moves up (-y)
		for (int y = piecePos.getY() - 1; y >= 0; y--) {

			Piece tempPiece = gameState.getPieceAt(new Position(piecePos.getX(), y));
			plpl.add(tempPiece.getPosition());
			if (tempPiece.getType() != PieceType.NONE && tempPiece.getType() != PieceType.KING) {
				break;
			}
		}

		// check for legal moves up (+x)
		for (int x = piecePos.getX() + 1; x < 8; x++) {

			Piece tempPiece = gameState.getPieceAt(new Position(x, piecePos.getY()));
			plpl.add(tempPiece.getPosition());
			if (tempPiece.getType() != PieceType.NONE && tempPiece.getType() != PieceType.KING) {
				break;
			}
		}

		// check for legal moves up (-x)
		for (int x = piecePos.getX() - 1; x >= 0; x--) {

			Piece tempPiece = gameState.getPieceAt(new Position(x, piecePos.getY()));
			plpl.add(tempPiece.getPosition());
			if (tempPiece.getType() != PieceType.NONE && tempPiece.getType() != PieceType.KING) {
				break;
			}
		}

		return plpl;
	}

	public static List<Position> Bishop(Piece piece, GameState gameState) {
		List<Position> plpl = new ArrayList<>();

		Position piecePos = piece.getPosition();

		// check for legal moves up, right (+y, +x)
		for (int y = 1; y < 8; y++) {
			final int staticY = piecePos.getY() + y;
			final int staticX = piecePos.getX() + y;
			if (staticX > 7 || staticY > 7) {
				break;
			}
			Piece tempPiece = gameState.getPieceAt(new Position(staticX, staticY));
			plpl.add(tempPiece.getPosition());
			if (tempPiece.getType() != PieceType.NONE && tempPiece.getType() != PieceType.KING) {
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
			plpl.add(tempPiece.getPosition());
			if (tempPiece.getType() != PieceType.NONE && tempPiece.getType() != PieceType.KING) {
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
			plpl.add(tempPiece.getPosition());
			if (tempPiece.getType() != PieceType.NONE && tempPiece.getType() != PieceType.KING) {
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
			plpl.add(tempPiece.getPosition());
			if (tempPiece.getType() != PieceType.NONE && tempPiece.getType() != PieceType.KING) {
				break;
			}
		}

		return plpl;
	}

	public static List<Position> Queen(Piece piece, GameState gameState) {
		List<Position> plpl = new ArrayList<>();

		plpl.addAll(AttackedPositions.Rook(piece, gameState));
		plpl.addAll(AttackedPositions.Bishop(piece, gameState));

		return plpl;
	}

	public static List<Position> Knight(Piece piece, GameState gameState) {

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

		return positionList;
	}

	public static List<Position> King(Piece piece, GameState gameState) {
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

		return positionList;
	}

	public static List<Position> Pawn(Piece piece, GameState gameState) {
		List<Position> plpl = new ArrayList<>();

		Position piecePos = piece.getPosition();

		if (piece.getColor() == PieceColor.WHITE) { // white pawn

			// no pawn upgrade
			if (piecePos.getY() != 6) {

				// take piece left
				Piece tempPiece3 = gameState.getPieceAt(new Position(piecePos.getX() - 1, piecePos.getY() + 1));
				plpl.add(tempPiece3.getPosition());

				// take piece right
				Piece tempPiece4 = gameState.getPieceAt(new Position(piecePos.getX() + 1, piecePos.getY() + 1));
				plpl.add(tempPiece4.getPosition());

				// pawn upgrade
			} else { // if (piecePos.getY() == 6)

				// take piece left
				Piece tempPiece3 = gameState.getPieceAt(new Position(piecePos.getX() - 1, piecePos.getY() + 1));
				plpl.add(tempPiece3.getPosition());

				// take piece right
				Piece tempPiece4 = gameState.getPieceAt(new Position(piecePos.getX() + 1, piecePos.getY() + 1));
				plpl.add(tempPiece4.getPosition());

			}

		} else if (piece.getColor() == PieceColor.BLACK) { // black pawn

			// no pawn upgrade
			if (piecePos.getY() != 1) {

				// take piece left
				Piece tempPiece3 = gameState.getPieceAt(new Position(piecePos.getX() - 1, piecePos.getY() - 1));
				plpl.add(tempPiece3.getPosition());

				// take piece right
				Piece tempPiece4 = gameState.getPieceAt(new Position(piecePos.getX() + 1, piecePos.getY() - 1));
				plpl.add(tempPiece4.getPosition());

				// pawn upgrade
			} else { // (piecePos.getY() == 1)

				// take piece left
				Piece tempPiece3 = gameState.getPieceAt(new Position(piecePos.getX() - 1, piecePos.getY() - 1));
				plpl.add(tempPiece3.getPosition());

				// take piece right
				Piece tempPiece4 = gameState.getPieceAt(new Position(piecePos.getX() + 1, piecePos.getY() - 1));
				plpl.add(tempPiece4.getPosition());

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
					plpl.add(gameState.getEnPassantTargetSquare());
				}
			}
		}

		return plpl;
	}

}
