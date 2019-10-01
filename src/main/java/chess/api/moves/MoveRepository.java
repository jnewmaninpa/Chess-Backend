package chess.api.moves;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import chess.api.exceptions.MoveNotValidException;
import chess.api.fen.FenRepository;
import chess.api.game.GameModel;
import chess.game.move.Move;
import chess.gamestate.GameState;
import chess.piece.properties.PieceType;
import chess.position.Position;

@Repository
public class MoveRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private FenRepository fenRepository;

	public void add(long id, Move move) {
		String query = "INSERT INTO public.moves(game_id, move) VALUES (?, ?);";
		jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException {
				ps.setLong(1, id);

				ObjectMapper mapper = new ObjectMapper();
				String moveString;
				try {
					moveString = mapper.writeValueAsString(move);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					moveString = null;
				}

				PGobject jsonObject = new PGobject();
				jsonObject.setType("json");
				jsonObject.setValue(moveString);

				ps.setObject(2, jsonObject);

				return ps.execute();
			}
		});
	}

	public MoveModel add(GameModel game, Move move, GameState gameState) {

		// create a MoveModel object
		MoveModel moveModel = new MoveModel(game, move, gameState);

		// update the FEN in the database
		fenRepository.update(moveModel.getGame().getId(), moveModel.getGame().getFen());

		// add the move to the database
		add(moveModel.getGame().getId(), moveModel.getMove());

		return moveModel;
	}

	public MoveModel add(GameModel game, Position initialPos, Position finalPos) throws MoveNotValidException {

		// create a MoveModel object
		MoveModel moveModel = new MoveModel(game, initialPos, finalPos);

		// update the FEN in the database
		fenRepository.update(moveModel.getGame().getId(), moveModel.getGame().getFen());

		// add the move to the database
		add(moveModel.getGame().getId(), moveModel.getMove());

		return moveModel;
	}

	public MoveModel add(GameModel game, Position initialPos, Position finalPos, PieceType newPieceType)
			throws MoveNotValidException {

		// create a MoveModel object
		MoveModel moveModel = new MoveModel(game, initialPos, finalPos, newPieceType);

		// update the FEN in the database
		fenRepository.update(moveModel.getGame().getId(), moveModel.getGame().getFen());

		// add the move to the database
		add(moveModel.getGame().getId(), moveModel.getMove());

		return moveModel;
	}

}
