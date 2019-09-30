package chess.api.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import chess.api.exceptions.IllegalUsernameExecption;
import chess.api.exceptions.UsernameAlreadyExistsException;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void add(UserModel user) throws UsernameAlreadyExistsException, IllegalUsernameExecption {
		String query = "INSERT INTO public.users(username, password) VALUES (?, ?);";

		// TODO what happens when the uses already exists???
		// Can we use this to determine that and then respond by throwing the proper
		// exception?
		try {
			jdbcTemplate.execute(query, new PreparedStatementCallback<Integer>() {
				@Override
				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException {
					ps.setString(1, user.getUsername());

					String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
					ps.setString(2, hashedPassword);

					return ps.executeUpdate();
				}
			});
		} catch (DataAccessException e) {
			if (e.getRootCause().toString().contains(
					"ERROR: new row for relation \"users\" violates check constraint \"users_username_check\"")) {
				throw new IllegalUsernameExecption("That username contains illegal characters");
			} else if (e.getRootCause().toString().startsWith("org.postgresql.util.PSQLException")) {
				throw new UsernameAlreadyExistsException("User already exists");
			} else {
				throw e;
			}
		}
	}

	public boolean checkPassword(UserModel user) {
		String query = "SELECT password FROM public.users where username=?";

		return jdbcTemplate.execute(query, (PreparedStatementCallback<Boolean>) ps -> {
			ps.setString(1, user.getUsername());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return BCrypt.checkpw(user.getPassword(), rs.getString("password"));
			} else {
				return false;
			}
		});

	}

}
