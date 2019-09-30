package chess.test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import chess.game.move.KingCastleMove;
import chess.game.move.Move;
import chess.game.move.PawnUpgradeMove;
import chess.gameState.FEN;
import chess.gameState.GameState;

public class Perft {

	public static void divide(int depth, GameState gameState) {
		// Get all of the pieces for the current player

		List<Move> movesList = gameState.getAllMoves();

		// for multi-threaded divide
		movesList.parallelStream().forEach(move -> {
			GameState tempGameState = gameState.copy();
			Move tempMove;
			if (move instanceof PawnUpgradeMove) {
				tempMove = new PawnUpgradeMove(tempGameState.getPieceAt(move.getPiece().getPosition()),
						move.getPosition(), ((PawnUpgradeMove) move).getUpgrade());
			} else if (move instanceof KingCastleMove) {
				tempMove = new KingCastleMove(tempGameState.getPieceAt(move.getPiece().getPosition()),
						move.getPosition());
			} else {
				tempMove = new Move(tempGameState.getPieceAt(move.getPiece().getPosition()), move.getPosition());
			}
			tempMove.move(tempGameState);
			long numberOfMoves = perft(depth - 1, tempGameState);
			System.out.print(tempMove + ": ");
			System.out.println(numberOfMoves);
		});

//		// for single-threaded divide
//		for (Move move : movesList) {
//			GameState tempGameState = gameState.copy();
//			Move tempMove;
//			if (move instanceof PawnUpgradeMove) {
//				tempMove = new PawnUpgradeMove(tempGameState.getPieceAt(move.getPiece().getPosition()), move.getPosition(), ((PawnUpgradeMove) move).getUpgrade());
//			} else if (move instanceof KingCastleMove) {
//				tempMove = new KingCastleMove(tempGameState.getPieceAt(move.getPiece().getPosition()), move.getPosition());
//			} else {
//				tempMove = new Move(tempGameState.getPieceAt(move.getPiece().getPosition()), move.getPosition());
//			}
//			tempMove.move(tempGameState);
//			System.out.print(tempMove + ": ");
//			System.out.println(perft(depth - 1, tempGameState));
//		}
	}

	public static long perft(int depth, GameState gameState) {
		// Get all of the pieces for the current player

		List<Move> movesList = gameState.getAllMoves();

		long totalNodes = 0L;

		if (depth == 0) {
			return 1;
		}

		// for multi-threaded perft
		totalNodes = movesList.parallelStream().mapToLong(move -> {
			GameState tempGameState = gameState.copy();
			Move tempMove;
			if (move instanceof PawnUpgradeMove) {
				tempMove = new PawnUpgradeMove(tempGameState.getPieceAt(move.getPiece().getPosition()),
						move.getPosition(), ((PawnUpgradeMove) move).getUpgrade());
			} else if (move instanceof KingCastleMove) {
				tempMove = new KingCastleMove(tempGameState.getPieceAt(move.getPiece().getPosition()),
						move.getPosition());
			} else {
				tempMove = new Move(tempGameState.getPieceAt(move.getPiece().getPosition()), move.getPosition());
			}
			tempMove.move(tempGameState);
			return perft(depth - 1, tempGameState);
		}).sum();

//		//for single threaded perft
//		for (Move move : movesList) {
//			GameState tempGameState = gameState.copy();
//			Move tempMove;
//			if (move instanceof PawnUpgradeMove) {
//				tempMove = new PawnUpgradeMove(tempGameState.getPieceAt(move.getPiece().getPosition()), move.getPosition(), ((PawnUpgradeMove) move).getUpgrade());
//			} else if (move instanceof KingCastleMove) {
//				tempMove = new KingCastleMove(tempGameState.getPieceAt(move.getPiece().getPosition()), move.getPosition());
//			} else {
//				tempMove = new Move(tempGameState.getPieceAt(move.getPiece().getPosition()), move.getPosition());
//			}
//			tempMove.move(tempGameState);
//			totalNodes += perft(depth - 1, tempGameState);
//		}
		return totalNodes;
	}

	public static void main(String... args) {

//		GameState game = new FEN().getGameState();

//		Game game = new Game("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
//		Game game = new Game("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");

		// Good test position (http://www.rocechess.ch/perft.html)
//		GameState game = FEN.getGameState("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
		GameState game = FEN.getGameState("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
		/*
		 * Expected Output:
		 * 
		 * Depth Nodes Total Nodes Perft 1: 48 48 Perft 2: 2039 2087 Perft 3: 97862
		 * 99949 Perft 4: 4085603 4185552 Perft 5: 193690690 197876242 Perft 6:
		 * 8031647685 8229523927
		 * 
		 */
		// e1f1
//		Game game = new Game("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R4K1R b kq - 0 1");

		// h3g2
//		Game game = new Game("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q2/PPPBBPpP/R4K1R w kq - 0 1");

		// Discover promotion bugs (http://www.rocechess.ch/perft.html)
//		Game game = new Game("n1n5/PPPk4/8/8/8/8/4Kppp/5N1N b - - 0 1");
		/*
		 * Expected Output:
		 * 
		 * Depth Nodes Total Nodes Perft 1: 24 24 Perft 2: 496 520 Perft 3: 9483 10003
		 * Perft 4: 182838 192841 Perft 5: 3605103 3797944 Perft 6: 71179139 74977083
		 * 
		 */

//		FEN.getGameState(game.getFen()).getPieceList().stream()
//		.filter(piece -> piece.getColor() == PieceColor.WHITE)
//		.forEach(piece -> System.out.println(piece + "   " + piece.getLegalMoves(FEN.getGameState(game.getFen()))));
//		Game game = new Game("8/8/1K3k2/8/8/8/4p3/3R4 b - - 0 1");

//		LocalTime time = LocalTime.now();
//		Perft.divide(3,game);
//		System.out.println(Duration.between(time, LocalTime.now()));

//		GameState game = FEN.getGameState("3P2kP/8/8/7P/6B1/2r1p3/8/6K1 w - - 152 77");

		LocalTime time = LocalTime.now();
		System.out.print("Perft 1: " + Perft.perft(1, game));
		System.out.println(" in " + Duration.between(LocalTime.now(), time) + "s");
		time = LocalTime.now();
		System.out.print("Perft 2: " + Perft.perft(2, game));
		System.out.println(" in " + Duration.between(LocalTime.now(), time) + "s");
		time = LocalTime.now();
		System.out.print("Perft 3: " + Perft.perft(3, game));
		System.out.println(" in " + Duration.between(LocalTime.now(), time) + "s");
		time = LocalTime.now();
		System.out.print("Perft 4: " + Perft.perft(4, game));
		System.out.println(" in " + Duration.between(LocalTime.now(), time) + "s");
		time = LocalTime.now();
		System.out.print("Perft 5: " + Perft.perft(5, game));
		System.out.println(" in " + Duration.between(LocalTime.now(), time) + "s");
		time = LocalTime.now();
		System.out.print("Perft 6: " + Perft.perft(6, game));
		System.out.println(" in " + Duration.between(LocalTime.now(), time) + "s");
	}

}
