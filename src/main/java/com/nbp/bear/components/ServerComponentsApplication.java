package com.nbp.bear.components;

import com.nbp.bear.components.model.NbpUser;
import com.nbp.bear.components.repository.NbpUserRepository;
import com.nbp.bear.components.util.NbpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.Date;
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

	@Value("${spring.profiles.active}")
	private String production;

	@PostConstruct
	public void initUsers() {
		if (production != "prod") {
			List<NbpUser> users = Stream.of(
					new NbpUser(1,  NbpUtil.NbpGenerateUserId(), "Nzeukang Randrin", "https://png.pngtree.com/png-clipart/20210129/ourmid/pngtree-graphic-default-avatar-png-image_2813121.jpg", "randrino", bCryptPasswordEncoder.encode("123456789"), "nzeukangrandrin@gmail.com", true, "ROLE_USER,ROLE_ADMIN",true, new Date(), new Date()),
					new NbpUser(2, NbpUtil.NbpGenerateUserId(), "Djomou Rodrigue", "https://png.pngtree.com/png-clipart/20210129/ourmid/pngtree-graphic-default-avatar-png-image_2813121.jpg", "rodrigo", bCryptPasswordEncoder.encode("123456789"), "djomoutresor1@hotmail.fr", true, "ROLE_USER,ROLE_ADMIN",true,  new Date(), new Date()),
					new NbpUser(3, NbpUtil.NbpGenerateUserId(), "Tchoumo Alain", "https://png.pngtree.com/png-clipart/20210129/ourmid/pngtree-graphic-default-avatar-png-image_2813121.jpg", "alino", bCryptPasswordEncoder.encode("123456789"), "alino@gmail.com", false, "ROLE_USER",false,  new Date(), new Date()),
					new NbpUser(4, NbpUtil.NbpGenerateUserId(), "Abah Yannick", "https://png.pngtree.com/png-clipart/20210129/ourmid/pngtree-graphic-default-avatar-png-image_2813121.jpg", "yannick", bCryptPasswordEncoder.encode("123456789"), "ayannick@gmail.com", true, "ROLE_USER",false,  new Date(), new Date()),
					new NbpUser(5, NbpUtil.NbpGenerateUserId(), "Nkenyne Chinda", "https://png.pngtree.com/png-clipart/20210129/ourmid/pngtree-graphic-default-avatar-png-image_2813121.jpg", "herve", bCryptPasswordEncoder.encode("123456789"), "herve@gmail.com", true, "ROLE_ADMIN",false,  new Date(), new Date())
			).collect(Collectors.toList());
			nbpUserRepository.saveAll(users);
		}
	}

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
						.allowedHeaders("*");
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerComponentsApplication.class, args);
	}

}
