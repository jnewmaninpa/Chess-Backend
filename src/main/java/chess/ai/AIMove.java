package chess.ai;

import java.util.List;
import java.util.Random;

import chess.game.move.KingCastleMove;
import chess.game.move.Move;
import chess.game.move.PawnUpgradeMove;
import chess.gamestate.GameState;
import chess.piece.properties.PieceType;

public class AIMove {
	
	private AIMove() {
		
	}
	
	private static Random random = new Random();

	public static Move aiNextMove(GameState gameState) {

		List<Move> moveList = gameState.getAllMoves();

		if (!moveList.isEmpty()) {
			int index = random.nextInt(moveList.size());
			return moveList.get(index);
		} else {
			return null;
		}
	}

	public static Move aiAgressiveMove(GameState gameState) {

		List<Move> moveList = gameState.getAllMoves();

		if (!moveList.isEmpty()) {
			// if there is a piece to take take it 75% of the time
			for (Move move : moveList) {
				if ((gameState.getPieceAt(move.getPosition()).getType() != PieceType.NONE) && (random.nextInt(99) <= 75)) {
					return move;
				}
			}
			// otherwise make a random move
			int index = random.nextInt(moveList.size());
			return moveList.get(index);
		} else {
			return null;
		}
	}

	public static Move aiCheckMove(GameState gameState) {

		List<Move> moveList = gameState.getAllMoves();

		if (!moveList.isEmpty()) {
			
			for (Move move : moveList) {
				GameState tempGameState = gameState.copy();
				Move tempMove = getTempMove(move, tempGameState);
				tempMove.move(tempGameState);
				// if the move will end the game always take it
				if (tempGameState.getGameOver()) {
					return move;
				}
			}
			
			for (Move move : moveList) {
				GameState tempGameState = gameState.copy();
				Move tempMove = getTempMove(move, tempGameState);
				tempMove.move(tempGameState);
				// if the move puts the player in check take it 90% of the time
				if (tempGameState.playerInCheck() && (random.nextInt(99) <= 90)) {
					return move;
				}
			}
			
			// if there is a piece to take take it 60% of the time
			for (Move move : moveList) {
				if ((gameState.getPieceAt(move.getPosition()).getType() != PieceType.NONE) && (random.nextInt(99) <= 60)) {
					return move;
				}
			}
			
			// otherwise advance a pawn 50% of the time
			for (Move move : moveList) {
				if ((move.getPiece().getType() == PieceType.PAWN) && (random.nextInt(99) <= 50)) {
					return move;
				}
			}
			
			// otherwise make a random move
			int index = random.nextInt(moveList.size());
			return moveList.get(index);
			
		} else {
			throw new GameOverException("The game is over " + gameState.getActiveColor() + " has not more moves");
		}
	}

	private static Move getTempMove(Move move, GameState tempGameState) {
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
		return tempMove;
	}

}
