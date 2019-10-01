package chess.api.moves.legal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import chess.api.exceptions.GameDoesNotExistException;
import chess.api.game.GameRepository;
import chess.position.Position;

@RestController
public class LegalMovesController {

	@Autowired
	private GameRepository gameRepository;

	@GetMapping("/legalMoves/{id}/{pos}")
	public LegalMovesModel move(@PathVariable long id, @PathVariable int pos) throws GameDoesNotExistException {
		return new LegalMovesModel(gameRepository.get(id), new Position(pos));
	}

}
