package chess.gameState;

import chess.piece.*;
import chess.piece.properties.PieceType;

import java.util.List;
import java.util.stream.Collectors;

/*
 * file/rank notation		Array notation			Number notation
 * 													
 * 8 | - - - - - - - -		7 | - - - - - - - -		8 | 56 57 58 59 60 61 62 63
 * 7 | - - - - - - - -		6 | - - - - - - - -		7 | 48 49 50 51 52 53 54 55
 * 6 | - - - - - - - -		5 | - - - - - - - -		6 | 40 41 42 43 44 45 46 47
 * 5 | - - - - - - - -		4 | - - - - - - - -		5 | 32 33 34 35 36 37 38 39
 * 4 | - - - - - - - -		3 | - - - - - - - -		4 | 24 25 26 27 28 29 30 31
 * 3 | - - - - - - - -		2 | - - - - - - - -		3 | 16 17 18 19 20 21 22 23
 * 2 | - - - - - - - -		1 | - - - - - - - -		2 | 08 09 10 11 12 13 14 15
 * 1 | - - - - - - - -		0 | - - - - - - - -		1 | 00 01 02 03 04 05 06 07
 *     a b c d e f g h		    0 1 2 3 4 5 6 7		     a  b  c  d  e  f  g  h
 */

public class Board {

	private Piece[][] board = new Piece[8][8]; // [rank][file] -> rank:0=1...7=8 file:0=a...7=h
	private GameState gameState;

	public Board(GameState gameState) {
		this.gameState = gameState;
		initializeBoard();
	}

	public Board(String fenString) {
		gameState = (new FEN(fenString)).getGameState();
		initializeBoard();
	}

	public Board() {
		gameState = (new FEN()).getGameState();
		initializeBoard();
	}

	private void initializeBoard() {

		// initialize empty board with None pieces
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new None((char) ('a' + j), 1 + i);
			}
		}

		// add pieces that exist to the board
		for (Piece piece : gameState.getPieceList()) {
			board[piece.getPosition().getY()][piece.getPosition().getX()] = piece;
		}
	}

	public void repaintBoard(GameState gameState) {
		this.gameState = gameState;
		initializeBoard();
	}

	public Piece[][] getBoard() {
		return board;
	}

	public GameState getGameState() {
		return gameState;
	}

	public boolean playerInCheck(GameState gameState) {
		List<Piece> kings = gameState.getPieceList().stream().filter(piece -> piece.getType() == PieceType.KING)
				.collect(Collectors.toList());

		for (Piece piece : kings) {
			if (((King) piece).check(gameState)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String string = "";
		for (int rank = 8; rank >= 1; rank--) {
			string += rank + "| ";
			for (Piece piece : this.board[rank - 1]) {
				string += piece + " ";
			}
			string += "\n";
		}
		string += "   ";
		for (char file = 'a'; file <= 'h'; file++) {
			string += "--";
		}
		string += "\n   ";
		for (char file = 'a'; file <= 'h'; file++) {
			string += file + " ";
		}

		return string;
	}

	public static void main(String[] args) {
		Board board = new Board();

		System.out.println(board);
	}

}
