package chess.api.moves;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import chess.ai.AIMove;
import chess.api.exceptions.GameDoesNotExistException;
import chess.api.exceptions.MoveNotValidException;
import chess.api.game.GameModel;
import chess.api.game.GameRepository;
import chess.game.move.Move;
import chess.gamestate.FEN;
import chess.gamestate.GameState;
import chess.piece.properties.PieceColor;
import chess.piece.properties.PieceType;
import chess.position.Position;

@RestController
public class MoveController {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private MoveRepository moveRepository;

	@GetMapping("/move/{id}/{initPos}/{finPos}")
	public MoveModel move(@PathVariable int id, @PathVariable int initPos, @PathVariable int finPos)
			throws MoveNotValidException, GameDoesNotExistException {
		return moveRepository.add(gameRepository.get(id), new Position(initPos), new Position(finPos));
	}

	@GetMapping("/move/{id}/{initPos}/{finPos}/{newPieceType}")
	public MoveModel move(@PathVariable int id, @PathVariable int initPos, @PathVariable int finPos,
			@PathVariable PieceType newPieceType) throws MoveNotValidException, GameDoesNotExistException {
		return moveRepository.add(gameRepository.get(id), new Position(initPos), new Position(finPos), newPieceType);
	}

	@GetMapping("/ai/move/{id}")
	public MoveModel aiMove(@PathVariable int id) throws GameDoesNotExistException {
		GameModel game = gameRepository.get(id);
		GameState gameState = FEN.getGameState(game.getFen());

		Move move;
		if (gameState.getActiveColor() == PieceColor.WHITE)
			move = AIMove.aiCheckMove(gameState);
		else
			move = AIMove.aiNextMove(gameState);

		if (move != null) {
			return moveRepository.add(game, move, gameState);
		} else {
			return null;
		}
	}

}
