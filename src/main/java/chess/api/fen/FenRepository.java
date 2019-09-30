package chess.api.fen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

@Repository
public class FenRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void update(long id, String fen) {
		String query = "UPDATE public.games SET fen=? WHERE id=?;";
		jdbcTemplate.execute(query, (PreparedStatementCallback<Boolean>) ps -> {
			ps.setString(1, fen);
			ps.setLong(2, id);
			return ps.execute();
		});
	}

}
