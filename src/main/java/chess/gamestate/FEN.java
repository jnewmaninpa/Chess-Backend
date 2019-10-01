package chess.gamestate;

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
	private final String fenString;

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
		this.fenString = fen;
	}

	// PUBLIC METHODS

	/**
	 * 
	 * @return Forsyth-Edwards Notation
	 */
	public String getFen() {
		return fenString;
	}

	/**
	 * 
	 * @return a list of pieces on the board
	 */
	public List<Piece> getPieceList() {
		return getPieceList(fenString);
	}

	/**
	 * 
	 * @return the active color
	 */
	public PieceColor getActiveColor() {
		return getActiveColor(fenString);
	}

	/**
	 * 
	 * @return castling availability
	 */
	public CastlingAvailability getCastlingAvailability() {
		return getCastlingAvailability(fenString);
	}

	/**
	 * 
	 * @return the en passant target square
	 */
	public Position getEnPassantTargetSquare() {
		return getEnPassantTargetSquare(fenString);
	}

	/**
	 * 
	 * @return the number of half moves since last capture or pawn advance
	 */
	public int getHalfmoveClock() {
		return getHalfmoveClock(fenString);
	}

	/**
	 * 
	 * @return the number of full moves, starts at 1, incremented after black's turn
	 */
	public int getFullmoveNumber() {
		return getFullmoveNumber(fenString);
	}

	/**
	 * 
	 * @return a GameState object
	 */
	public GameState getGameState() {
		return getGameState(fenString);
	}

	// PUBLIC STATIC METHODS

	public static String getFen(GameState gameState) {
		StringBuilder tempFen = new StringBuilder();
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
						tempFen.append(spacesSkipped);
						spacesSkipped = 0;
					}
					tempFen.append(gameState.getPieceList().get(currentPieceNumber).getRepresentation());
					currentPieceNumber++;
				} else {
					spacesSkipped++;
				}
			}
			if (spacesSkipped != 0) {
				tempFen.append(spacesSkipped);
				spacesSkipped = 0;
			}
			if (rankNumber != 1) {
				tempFen.append("/");
			} else {
				tempFen.append(" ");
			}
		}

		if (gameState.getActiveColor() == PieceColor.WHITE) {
			tempFen.append("w");
		} else if (gameState.getActiveColor() == PieceColor.BLACK) {
			tempFen.append("b");
		}

		tempFen.append(" ");

		if (gameState.getCastlingAvailability().getWhiteKingside()) {
			tempFen.append("K");
		}
		if (gameState.getCastlingAvailability().getWhiteQueenside()) {
			tempFen.append("Q");
		}
		if (gameState.getCastlingAvailability().getBlackKingside()) {
			tempFen.append("k");
		}
		if (gameState.getCastlingAvailability().getBlackQueenside()) {
			tempFen.append("q");
		}
		if (!gameState.getCastlingAvailability().getWhiteKingside()
				&& !gameState.getCastlingAvailability().getWhiteQueenside()
				&& !gameState.getCastlingAvailability().getBlackKingside()
				&& !gameState.getCastlingAvailability().getBlackQueenside()) {
			tempFen.append("-");
		}

		tempFen.append(" ");

		if (gameState.getEnPassantTargetSquare() == null) {
			tempFen.append("-");
		} else {
			tempFen.append(gameState.getEnPassantTargetSquare().toString());
		}

		tempFen.append(" ");

		tempFen.append(gameState.getHalfmoveClock());

		tempFen.append(" ");

		tempFen.append(gameState.getFullmoveNumber());

		return tempFen.toString();
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
				case "7":
				case "6":
				case "5":
				case "4":
				case "3":
				case "2":
				case "1":
					fileLetter += Integer.parseInt(piece) - 1;
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
				default:
					throw new InvalidFenStringException("The provided fen contained an invalid character");
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
			default:
				throw new InvalidCastlingAvailabilityStringException("The provided castling availability string contained an illegal character");
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
