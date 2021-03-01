package com.nbp.bear.components;

import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.repository.NbpUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ServerComponentsApplication {

	// Test Save Data to H2 Database
	@Autowired
	private NbpUserRepository nbpUserRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	public void initUsers() {
		List<NbpUser> users = Stream.of(
				new NbpUser(1, "Randrino", bCryptPasswordEncoder.encode("123456789"), "nzeukangrandrin@gmail.com", true, "ROLE_USER,ROLE_ADMIN",false),
				new NbpUser(2, "Rodrigo", bCryptPasswordEncoder.encode("123456789"), "djomoutresor1@hotmail.fr", true, "ROLE_USER,ROLE_ADMIN",false),
				new NbpUser(3, "Alino", bCryptPasswordEncoder.encode("123456789"), "alino@gmail.com", false, "ROLE_USER",false),
				new NbpUser(4, "Yannick", bCryptPasswordEncoder.encode("123456789"), "ayannick@gmail.com", true, "ROLE_USER",false),
				new NbpUser(5, "Hervé", bCryptPasswordEncoder.encode("123456789"), "herve@gmail.com", true, "ROLE_ADMIN",false)
		).collect(Collectors.toList());
		nbpUserRepository.saveAll(users);
	}

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*");
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerComponentsApplication.class, args);
	}

}
