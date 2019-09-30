package chess.api.game;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import chess.api.exceptions.GameDoesNotExistException;
import chess.api.exceptions.GameNotCreatedException;
import chess.api.exceptions.NumberOfGamesNotFoundException;

@RestController
public class GameController {

	@Autowired
	private GameRepository gameRepository;

	@GetMapping("/game/{id}")
	public GameModel existingGame(@PathVariable long id) throws GameNotCreatedException {
		try {
			return gameRepository.get(id);
		} catch (GameDoesNotExistException e) {
			return gameRepository.createAndGetNew(id);
		}

	}

	@GetMapping("/game")
	public GameModel newGame() {

		GameModel result;
		int count = 0;

		do {
			try {
				result = gameRepository.createAndGetNew();
			} catch (Exception e) {
				result = null;
			}
			count++; // this is to insure this loop ends eventually if there is a larger error at
						// play

		} while ((result == null) && count < 100000);

		return result;
	}

	@GetMapping("/numberOfGames")
	public long numberOfGames() throws NumberOfGamesNotFoundException {
		return gameRepository.getNumber();
	}

	@GetMapping("/listOfGames")
	public List<Integer> getListOfGames() {
		return gameRepository.getListOfGames();
	}

}
