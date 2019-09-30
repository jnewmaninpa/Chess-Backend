package chess.gameState;

import java.util.List;

import chess.position.Position;
import chess.piece.*;
import chess.piece.properties.PieceColor;
import chess.piece.sort.SortByPosition;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
 * 
 * @author Justin Newman
 */
public class FEN {

	private static final String STARTING_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	private final String fen;

	// CONSTRUCTORS

	/**
	 * 
	 */
	public FEN() {
		this(STARTING_FEN);
	}

	/**
	 * 
	 * @param fen Forsyth-Edwards Notation
	 */
	public FEN(String fen) {
		this.fen = fen;
	}

	// PUBLIC METHODS

	/**
	 * 
	 * @return Forsyth-Edwards Notation
	 */
	public String getFen() {
		return fen;
	}

	/**
	 * 
	 * @return a list of pieces on the board
	 */
	public List<Piece> getPieceList() {
		return getPieceList(fen);
	}

	/**
	 * 
	 * @return the active color
	 */
	public PieceColor getActiveColor() {
		return getActiveColor(fen);
	}

	/**
	 * 
	 * @return castling availability
	 */
	public CastlingAvailability getCastlingAvailability() {
		return getCastlingAvailability(fen);
	}

	/**
	 * 
	 * @return the en passant target square
	 */
	public Position getEnPassantTargetSquare() {
		return getEnPassantTargetSquare(fen);
	}

	/**
	 * 
	 * @return the number of half moves since last capture or pawn advance
	 */
	public int getHalfmoveClock() {
		return getHalfmoveClock(fen);
	}

	/**
	 * 
	 * @return the number of full moves, starts at 1, incremented after black's turn
	 */
	public int getFullmoveNumber() {
		return getFullmoveNumber(fen);
	}

	/**
	 * 
	 * @return a GameState object
	 */
	public GameState getGameState() {
		return getGameState(fen);
	}

	// PUBLIC STATIC METHODS

	public static String getFen(GameState gameState) {
		String tempFen = "";
		Collections.sort(gameState.getPieceList(), new SortByPosition());

		int numberOfPieces = gameState.getPieceList().size();
		int currentPieceNumber = 0;
		int spacesSkipped = 0;

		for (int rankNumber = 8; rankNumber >= 1; rankNumber--) {
			for (char fileLetter = 'a'; fileLetter <= 'h'; fileLetter++) {
				if ((currentPieceNumber < numberOfPieces)
						&& (gameState.getPieceList().get(currentPieceNumber).getPosition().getRank() == rankNumber)
						&& (gameState.getPieceList().get(currentPieceNumber).getPosition().getFile() == fileLetter)) {
					if (spacesSkipped != 0) {
						tempFen += spacesSkipped;
						spacesSkipped = 0;
					}
					tempFen += gameState.getPieceList().get(currentPieceNumber).getRepresentation();
					currentPieceNumber++;
				} else {
					spacesSkipped++;
				}
			}
			if (spacesSkipped != 0) {
				tempFen += spacesSkipped;
				spacesSkipped = 0;
			}
			if (rankNumber != 1) {
				tempFen += "/";
			} else {
				tempFen += " ";
			}
		}

		if (gameState.getActiveColor() == PieceColor.WHITE) {
			tempFen += "w";
		} else if (gameState.getActiveColor() == PieceColor.BLACK) {
			tempFen += "b";
		}

		tempFen += " ";

		if (gameState.getCastlingAvailability().getWhiteKingside()) {
			tempFen += "K";
		}
		if (gameState.getCastlingAvailability().getWhiteQueenside()) {
			tempFen += "Q";
		}
		if (gameState.getCastlingAvailability().getBlackKingside()) {
			tempFen += "k";
		}
		if (gameState.getCastlingAvailability().getBlackQueenside()) {
			tempFen += "q";
		}
		if (!gameState.getCastlingAvailability().getWhiteKingside()
				&& !gameState.getCastlingAvailability().getWhiteQueenside()
				&& !gameState.getCastlingAvailability().getBlackKingside()
				&& !gameState.getCastlingAvailability().getBlackQueenside()) {
			tempFen += "-";
		}

		tempFen += " ";

		if (gameState.getEnPassantTargetSquare() == null) {
			tempFen += "-";
		} else {
			tempFen += gameState.getEnPassantTargetSquare().toString();
		}

		tempFen += " ";

		tempFen += gameState.getHalfmoveClock();

		tempFen += " ";

		tempFen += gameState.getFullmoveNumber();

		return tempFen;
	}

	public static List<Piece> getPieceList(String fen) {
		List<Piece> pieceList = new ArrayList<>();
		int rankNumber = 8;

		String[] ranks = fen.split(" ")[0].split("/");
		for (String rank : ranks) {
			char fileLetter = 'a';
			for (String piece : rank.split("")) {
				switch (piece) {
				case "8":
					fileLetter++;
				case "7":
					fileLetter++;
				case "6":
					fileLetter++;
				case "5":
					fileLetter++;
				case "4":
					fileLetter++;
				case "3":
					fileLetter++;
				case "2":
					fileLetter++;
				case "1":
					break;
				case "r":
					pieceList.add(new Rook(fileLetter, rankNumber, PieceColor.BLACK));
					break;
				case "R":
					pieceList.add(new Rook(fileLetter, rankNumber, PieceColor.WHITE));
					break;
				case "n":
					pieceList.add(new Knight(fileLetter, rankNumber, PieceColor.BLACK));
					break;
				case "N":
					pieceList.add(new Knight(fileLetter, rankNumber, PieceColor.WHITE));
					break;
				case "b":
					pieceList.add(new Bishop(fileLetter, rankNumber, PieceColor.BLACK));
					break;
				case "B":
					pieceList.add(new Bishop(fileLetter, rankNumber, PieceColor.WHITE));
					break;
				case "q":
					pieceList.add(new Queen(fileLetter, rankNumber, PieceColor.BLACK));
					break;
				case "Q":
					pieceList.add(new Queen(fileLetter, rankNumber, PieceColor.WHITE));
					break;
				case "k":
					pieceList.add(new King(fileLetter, rankNumber, PieceColor.BLACK));
					break;
				case "K":
					pieceList.add(new King(fileLetter, rankNumber, PieceColor.WHITE));
					break;
				case "p":
					pieceList.add(new Pawn(fileLetter, rankNumber, PieceColor.BLACK));
					break;
				case "P":
					pieceList.add(new Pawn(fileLetter, rankNumber, PieceColor.WHITE));
					break;
				}
				fileLetter++;
			}
			rankNumber--;
		}
		return pieceList;
	}

	public static PieceColor getActiveColor(String fen) {
		switch (fen.split(" ")[1]) {
		case "w":
			return PieceColor.WHITE;
		case "b":
			return PieceColor.BLACK;
		default:
			return PieceColor.NONE;
		}
	}

	public static CastlingAvailability getCastlingAvailability(String fen) {
		String casAv = fen.split(" ")[2];
		boolean whiteKingside = false;
		boolean whiteQueenside = false;
		boolean blackKingside = false;
		boolean blackQueenside = false;

		for (String temp : casAv.split("")) {
			switch (temp) {
			case "-":
				break;
			case "K":
				whiteKingside = true;
				break;
			case "Q":
				whiteQueenside = true;
				break;
			case "k":
				blackKingside = true;
				break;
			case "q":
				blackQueenside = true;
				break;
			}
		}

		return new CastlingAvailability.Builder().withWhiteKingside(whiteKingside).withWhiteQueenside(whiteQueenside)
				.withBlackKingside(blackKingside).withBlackQueenside(blackQueenside).build();
	}

	public static Position getEnPassantTargetSquare(String fen) {
		String enPas = fen.split(" ")[3];
		if (enPas.equals("-")) {
			return null;
		} else {
			return new Position(enPas.charAt(0), Integer.valueOf(enPas.split("")[1]));
		}
	}

	public static int getHalfmoveClock(String fen) {
		return Integer.valueOf(fen.split(" ")[4]);
	}

	public static int getFullmoveNumber(String fen) {
		return Integer.valueOf(fen.split(" ")[5]);
	}

	public static GameState getGameState(String fen) {
		return new GameState.Builder().withActiveColor(FEN.getActiveColor(fen)).withPieceList(FEN.getPieceList(fen))
				.withCastlingAvailability(FEN.getCastlingAvailability(fen))
				.withEnPassantTargetSquare(FEN.getEnPassantTargetSquare(fen))
				.withHalfmoveClock(FEN.getHalfmoveClock(fen)).withFullmoveNumber(FEN.getFullmoveNumber(fen)).build();
	}
}
