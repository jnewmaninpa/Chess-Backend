package chess.move.logic;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import chess.game.move.KingCastleMove;
import chess.game.move.Move;
import chess.gamestate.GameState;
import chess.piece.Piece;
import chess.piece.King;
import chess.piece.properties.PieceColor;
import chess.piece.properties.PieceType;
import chess.position.Position;

public class Legal {
	
	private Legal() {
		
	}

	public static List<Move> rook(Piece piece, GameState gameState) {
		List<Move> legalMoves;

		if (piece.myKingInDoubleCheck(gameState)) {
			legalMoves = new ArrayList<>();
		} else {
			// Check if moving this piece will put the king in check
			legalMoves = PsuedoLegal.rook(piece, gameState);
			legalMoves = legalMoves.stream().filter(mov -> {
				GameState tempGameState = gameState.copy();
				Piece tempPiece = tempGameState.getPieceAt(piece.getPosition());
				Move move = new Move(tempPiece, mov.getPosition());
				move.move(tempGameState);
				return !tempPiece.myKingInCheck(tempGameState);
			}).collect(Collectors.toList());
		}
		return legalMoves;
	}

	public static List<Move> bishop(Piece piece, GameState gameState) {
		List<Move> legalMoves;

		if (piece.myKingInDoubleCheck(gameState)) {
			legalMoves = new ArrayList<>();
		} else {
			// Check if moving this piece will put the king in check
			legalMoves = PsuedoLegal.bishop(piece, gameState);
			legalMoves = legalMoves.stream().filter(mov -> {
				GameState tempGameState = gameState.copy();
				Piece tempPiece = tempGameState.getPieceAt(piece.getPosition());
				Move move = new Move(tempPiece, mov.getPosition());
				move.move(tempGameState);
				return !tempPiece.myKingInCheck(tempGameState);
			}).collect(Collectors.toList());
		}
		return legalMoves;
	}

	public static List<Move> queen(Piece piece, GameState gameState) {
		List<Move> legalMoves;

		if (piece.myKingInDoubleCheck(gameState)) {
			legalMoves = new ArrayList<>();
		} else {
			// Check if moving this piece will put the king in check
			legalMoves = PsuedoLegal.queen(piece, gameState);
			legalMoves = legalMoves.stream().filter(mov -> {
				GameState tempGameState = gameState.copy();
				Piece tempPiece = tempGameState.getPieceAt(piece.getPosition());
				Move move = new Move(tempPiece, mov.getPosition());
				move.move(tempGameState);
				return !tempPiece.myKingInCheck(tempGameState);
			}).collect(Collectors.toList());
		}

		return legalMoves;
	}

	public static List<Move> knight(Piece piece, GameState gameState) {
		List<Move> legalMoves;

		if (piece.myKingInDoubleCheck(gameState)) {
			legalMoves = new ArrayList<>();
		} else {
			// Check if moving this piece will put the king in check
			legalMoves = PsuedoLegal.knight(piece, gameState);
			legalMoves = legalMoves.stream().filter(mov -> {
				GameState tempGameState = gameState.copy();
				Piece tempPiece = tempGameState.getPieceAt(piece.getPosition());
				Move move = new Move(tempPiece, mov.getPosition());
				move.move(tempGameState);
				return !tempPiece.myKingInCheck(tempGameState);
			}).collect(Collectors.toList());
		}
		return legalMoves;
	}

	public static List<Move> king(Piece piece, GameState gameState) {
		List<Move> pseudoLegalMoves = piece.getPseudoLegalMoves(gameState);

		if (!((King) piece).check(gameState)) {
			// add castling if applicable
			if (piece.getColor() == PieceColor.WHITE) {
				if (gameState.getCastlingAvailability().getWhiteKingside() &&
						(gameState.getPieceAt(new Position('f', 1)).getType() == PieceType.NONE) &&
						(gameState.getPieceAt(new Position('g', 1)).getType() == PieceType.NONE)) {
					pseudoLegalMoves.add(new KingCastleMove(piece, new Position('g', 1)));
				}
				if (gameState.getCastlingAvailability().getWhiteQueenside() &&
						(gameState.getPieceAt(new Position('b', 1)).getType() == PieceType.NONE) &&
						(gameState.getPieceAt(new Position('c', 1)).getType() == PieceType.NONE) &&
						(gameState.getPieceAt(new Position('d', 1)).getType() == PieceType.NONE)) {
					pseudoLegalMoves.add(new KingCastleMove(piece, new Position('c', 1)));
				}
			} else {
				if (gameState.getCastlingAvailability().getBlackKingside() &&
						(gameState.getPieceAt(new Position('f', 8)).getType() == PieceType.NONE) &&
						(gameState.getPieceAt(new Position('g', 8)).getType() == PieceType.NONE)) {
					pseudoLegalMoves.add(new KingCastleMove(piece, new Position('g', 8)));
				}
				if (gameState.getCastlingAvailability().getBlackQueenside() &&
						(gameState.getPieceAt(new Position('b', 8)).getType() == PieceType.NONE) &&
						(gameState.getPieceAt(new Position('c', 8)).getType() == PieceType.NONE) &&
						(gameState.getPieceAt(new Position('d', 8)).getType() == PieceType.NONE)) {
					pseudoLegalMoves.add(new KingCastleMove(piece, new Position('c', 8)));
				}
			}
		}
		List<Piece> opponentPieces = new ArrayList<>();

		for (Piece tempPiece : gameState.getPieceList()) {
			if (piece.getColor() != tempPiece.getColor()) {
				opponentPieces.add(tempPiece);
			}
		}

		Set<Position> kingDangerSquares = new LinkedHashSet<>();

		for (Piece tempPiece : opponentPieces) {
			kingDangerSquares.addAll(tempPiece.getKingDangerSquares(gameState));
		}

		List<Move> legalMoves = new ArrayList<>(pseudoLegalMoves);
		for (Move move : pseudoLegalMoves) {
			for (Position danger : kingDangerSquares) {
				if (move.getPosition().equals(danger)) {
					if (move instanceof KingCastleMove) {
						legalMoves.remove(new KingCastleMove(piece, danger));
					} else {
						legalMoves.remove(new Move(piece, danger));
					}
				}
				// remove castle move if the king moves through check
				if (move instanceof KingCastleMove) {
					if (move.getPosition().equals(new Position('c', 1)) && danger.equals(new Position('d', 1))) {
						legalMoves.remove(new KingCastleMove(piece, new Position('c', 1)));
					}
					if (move.getPosition().equals(new Position('g', 1)) && danger.equals(new Position('f', 1))) {
						legalMoves.remove(new KingCastleMove(piece, new Position('g', 1)));
					}
					if (move.getPosition().equals(new Position('c', 8)) && danger.equals(new Position('d', 8))) {
						legalMoves.remove(new KingCastleMove(piece, new Position('c', 8)));
					}
					if (move.getPosition().equals(new Position('g', 8)) && danger.equals(new Position('f', 8))) {
						legalMoves.remove(new KingCastleMove(piece, new Position('g', 8)));
					}
				}
			}
		}

		return legalMoves;
	}

	public static List<Move> pawn(Piece piece, GameState gameState) {
		List<Move> legalMoves;

		if (piece.myKingInDoubleCheck(gameState)) {
			legalMoves = new ArrayList<>();
		} else {
			// Check if moving this piece will put the king in check
			legalMoves = PsuedoLegal.pawn(piece, gameState);

			legalMoves = legalMoves.stream().filter(mov -> {
				GameState tempGameState = gameState.copy();
				Piece tempPiece = tempGameState.getPieceAt(piece.getPosition());
				Move move = new Move(tempPiece, mov.getPosition());
				move.move(tempGameState);
				return !tempPiece.myKingInCheck(tempGameState);
			}).collect(Collectors.toList());
		}

		return legalMoves;
	}

}
