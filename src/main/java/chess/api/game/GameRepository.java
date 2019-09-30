package chess.api.game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import chess.api.exceptions.GameDoesNotExistException;
import chess.api.exceptions.GameNotCreatedException;
import chess.api.exceptions.NumberOfGamesNotFoundException;

@Repository
public class GameRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public GameModel get(long id) throws GameDoesNotExistException {
		String query = "SELECT fen FROM public.games WHERE id=?;";
		GameModel game = jdbcTemplate.execute(query, new PreparedStatementCallback<GameModel>() {
			@Override
			public GameModel doInPreparedStatement(PreparedStatement ps) throws SQLException {
				ps.setLong(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						return new GameModel(id, rs.getString("fen"));
					} else {
						return null;
					}
				}

			}
		});

		// Check if a game was returned
		if (game != null) {
			return game;
		} else {
			throw new GameDoesNotExistException("A game with that id was not found in the database");
		}

	}

	public long getNumber() throws NumberOfGamesNotFoundException {
		String query = "SELECT COUNT(id) FROM public.games;";
		Long number = jdbcTemplate.execute(query, new PreparedStatementCallback<Long>() {
			@Override
			public Long doInPreparedStatement(PreparedStatement ps) throws SQLException {
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						return rs.getLong(1);
					} else {
						return null;
					}
				}

			}
		});

		if (number != null) {
			return number;
		} else {
			throw new NumberOfGamesNotFoundException("The number of games was not found");
		}
	}

	public void createNew() throws GameNotCreatedException {
		String query = "INSERT INTO public.games DEFAULT VALUES";
		if (jdbcTemplate.update(query) != 1) {
			throw new GameNotCreatedException("A new game could not be created");
		}
	}

	public GameModel createAndGetNew() throws GameNotCreatedException {
		String query = "INSERT INTO public.games DEFAULT VALUES RETURNING id, fen;";
		GameModel game = jdbcTemplate.execute(query, new PreparedStatementCallback<GameModel>() {
			@Override
			public GameModel doInPreparedStatement(PreparedStatement ps) throws SQLException {
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						return new GameModel(rs.getLong("id"), rs.getString("fen"));
					} else {
						return null;
					}
				}

			}
		});

		if (game != null) {
			return game;
		} else {
			throw new GameNotCreatedException("A new game could not be created");

		}

	}

	public GameModel createAndGetNew(long id) throws GameNotCreatedException {
		String query = "INSERT INTO public.games(id) VALUES (?) RETURNING fen;";
		GameModel game = jdbcTemplate.execute(query, new PreparedStatementCallback<GameModel>() {
			@Override
			public GameModel doInPreparedStatement(PreparedStatement ps) throws SQLException {
				ps.setLong(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						return new GameModel(id, rs.getString("fen"));
					} else {
						return null;
					}
				}
			}
		});

		if (game != null) {
			return game;
		} else {
			throw new GameNotCreatedException("A new game with id " + id + " could not be created");

		}

	}

	public List<Integer> getListOfGames() {
		String query = "SELECT id FROM games;";
		List<Integer> list = jdbcTemplate.queryForList(query, Integer.class);
		Collections.sort(list);
		return list;
	}

}
