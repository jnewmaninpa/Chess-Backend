package chess;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config {

	@Value("${frontend.url}")
	private String url;

	@Bean
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/game/{id}").allowedOrigins(url);
				registry.addMapping("/game").allowedOrigins(url);
				registry.addMapping("/numberOfGames").allowedOrigins(url);
				registry.addMapping("/listOfGames").allowedOrigins(url);
				registry.addMapping("/move/{id}/{initPos}/{finPos}").allowedOrigins(url);
				registry.addMapping("/move/{id}/{initPos}/{finPos}/{newPieceType}").allowedOrigins(url);
				registry.addMapping("/ai/move/{id}").allowedOrigins(url);
				registry.addMapping("/legalMoves/{id}/{pos}").allowedOrigins(url);
				registry.addMapping("/user/new").allowedOrigins(url);
				registry.addMapping("/user/login").allowedOrigins(url);
			}
		};
	}

}
