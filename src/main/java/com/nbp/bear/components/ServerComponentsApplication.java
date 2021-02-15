package com.nbp.bear.components;

import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.repository.NbpUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ServerComponentsApplication {

	// Test Save Data to H2 Database
	@Autowired
	private NbpUserRepository nbpUserRepository;

	@PostConstruct
	public void initUsers() {
		List<NbpUser> users = Stream.of(
				new NbpUser(1, "Randrino", "123456789", "nzeukangrandrin@gmail.com"),
				new NbpUser(2, "Rodrigo", "123456789", "djomoutresor1@gmail.com"),
				new NbpUser(3, "Alino", "123456789", "alino@gmail.com")
		).collect(Collectors.toList());
		nbpUserRepository.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerComponentsApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder () {
		return NoOpPasswordEncoder.getInstance();
	}
}
