package chess.api.moves;

import chess.api.exceptions.MoveNotValidException;
import chess.api.game.GameModel;
import chess.game.move.KingCastleMove;
import chess.game.move.Move;
import chess.game.move.PawnUpgradeMove;
import chess.gamestate.FEN;
import chess.gamestate.GameState;
import chess.piece.King;
import chess.piece.Pawn;
import chess.piece.properties.PieceColor;
import chess.piece.properties.PieceType;
import chess.position.Position;

public class MoveModel {

	private final boolean isValid;
	private final GameModel game;
	private final Move move;

	public MoveModel(GameModel game, Move newMove, GameState gameState) {
		this.game = game;
		this.move = newMove;

		// DO NOT CHECK IF MOVE IS VALID HERE BECAUSE ALL MOVES SHOULD BE VALID
		isValid = true;

		move.move(gameState);

		game.setFen(FEN.getFen(gameState));
	}

	public MoveModel(GameModel game, Position initialPos, Position finalPos) throws MoveNotValidException {

		this.game = game;

		GameState gameState = FEN.getGameState(game.getFen());

		this.isValid = gameState.getPieceAt(initialPos).moveValid(gameState, finalPos);

		if (this.isValid) {
			if (gameState.getPieceAt(initialPos) instanceof King
					&& (initialPos.getX() - finalPos.getX() == 2 || initialPos.getX() - finalPos.getX() == -2)) {
				move = new KingCastleMove(gameState.getPieceAt(initialPos), finalPos);
			} else {
				move = new Move(gameState.getPieceAt(initialPos), finalPos);
			}
			move.move(gameState);

			game.setFen(FEN.getFen(gameState));
		} else {
			throw new MoveNotValidException("The move was not valid");
		}

	}

	public MoveModel(GameModel game, Position initialPos, Position finalPos, PieceType newPieceType)
			throws MoveNotValidException {

		this.game = game;

		GameState gameState = FEN.getGameState(game.getFen());

		this.isValid = gameState.getPieceAt(initialPos).moveValid(gameState, finalPos);

		if (this.isValid) {
			if (gameState.getPieceAt(initialPos) instanceof King
					&& (initialPos.getX() - finalPos.getX() == 2 || initialPos.getX() - finalPos.getX() == -2)) {
				move = new KingCastleMove(gameState.getPieceAt(initialPos), finalPos);
			} else if (gameState.getPieceAt(initialPos) instanceof Pawn
					&& ((gameState.getPieceAt(initialPos).getColor() == PieceColor.WHITE && initialPos.getY() == 6)
							|| (gameState.getPieceAt(initialPos).getColor() == PieceColor.BLACK
									&& initialPos.getY() == 1))) {
				move = new PawnUpgradeMove(gameState.getPieceAt(initialPos), finalPos, newPieceType);
			} else {
				move = new Move(gameState.getPieceAt(initialPos), finalPos);
			}
			move.move(gameState);

			game.setFen(FEN.getFen(gameState));
		} else {
			throw new MoveNotValidException("The move was not valid");
		}

	}

	public boolean getIsValid() {
		return isValid;
	}

	public GameModel getGame() {
		return game;
	}

	public Move getMove() {
		return move;
	}

}
