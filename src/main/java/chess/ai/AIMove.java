package chess.ai;

import java.util.List;
import java.util.Random;

import chess.game.move.KingCastleMove;
import chess.game.move.Move;
import chess.game.move.PawnUpgradeMove;
import chess.gameState.GameState;
import chess.piece.properties.PieceType;

public class AIMove {

	public static Move AINextMove(GameState gameState) {

		Random random = new Random();
		List<Move> moveList = gameState.getAllMoves();

		if (moveList.size() > 0) {
			int index = random.nextInt(moveList.size());
			return moveList.get(index);
		} else {
			return null;
		}
	}

	public static Move AIAgressiveMove(GameState gameState) {

		Random random = new Random();
		List<Move> moveList = gameState.getAllMoves();

		if (moveList.size() > 0) {
			// if there is a piece to take take it 75% of the time
			for (Move move : moveList) {
				if (gameState.getPieceAt(move.getPosition()).getType() != PieceType.NONE) {
					if (random.nextInt(99) <= 75) { // 75% of the time
						return move;
					}
				}
			}
			// otherwise make a random move
			int index = random.nextInt(moveList.size());
			return moveList.get(index);
		} else {
			return null;
		}
	}

	public static Move AICheckMove(GameState gameState) {

		Random random = new Random();
		List<Move> moveList = gameState.getAllMoves();

		if (moveList.size() > 0) {
			// if there is a piece to take take it 90% of the time
			for (Move move : moveList) {
				GameState tempGameState = gameState.copy();
				Move tempMove;
				if (move instanceof KingCastleMove) {
					tempMove = new KingCastleMove(tempGameState.getPieceAt(move.getPieceInitialPosition()),
							move.getPosition());
				} else if (move instanceof PawnUpgradeMove) {
					tempMove = new PawnUpgradeMove(tempGameState.getPieceAt(move.getPieceInitialPosition()),
							move.getPosition(), ((PawnUpgradeMove) move).getUpgrade());
				} else {
					tempMove = new Move(tempGameState.getPieceAt(move.getPieceInitialPosition()), move.getPosition());
				}
				tempMove.move(tempGameState);
				// if the move will end the game always take it
				if (tempGameState.getGameOver()) {
					return move;
				}
				// if the move puts the player in check take it 90% of the time
				if (tempGameState.playerInCheck()) {
					if (random.nextInt(99) <= 90) { // 90% of the time
						return move;
					}
				}
			}
			// if there is a piece to take take it 60% of the time
			for (Move move : moveList) {
				if (gameState.getPieceAt(move.getPosition()).getType() != PieceType.NONE) {
					if (random.nextInt(99) <= 60) { // 60% of the time
						return move;
					}
				}
			}
			// otherwise advance a pawn 50& of the time
			for (Move move : moveList) {
				if (move.getPiece().getType() == PieceType.PAWN) {
					if (random.nextInt(99) <= 50) { // 50% of the time
						return move;
					}
				}
			}
			// otherwise make a random move
			int index = random.nextInt(moveList.size());
			return moveList.get(index);
		} else {
			return null;
		}
	}

}
