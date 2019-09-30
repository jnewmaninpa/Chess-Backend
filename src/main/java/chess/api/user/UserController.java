package chess.api.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import chess.api.exceptions.IllegalUsernameExecption;
import chess.api.exceptions.UsernameAlreadyExistsException;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/user/new")
	public UserModel newUser(@RequestBody UserModel user)
			throws UsernameAlreadyExistsException, IllegalUsernameExecption {

		userRepository.add(user);
		return user;

	}

	@PostMapping("/user/login")
	public boolean login(@RequestBody UserModel user) {
		return userRepository.checkPassword(user);
	}

}
