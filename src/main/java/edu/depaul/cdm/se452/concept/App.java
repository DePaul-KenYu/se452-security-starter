package edu.depaul.cdm.se452.concept;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.depaul.cdm.se452.concept.security.AppUser;
import edu.depaul.cdm.se452.concept.security.UserRepository;
import edu.depaul.cdm.se452.concept.security.UserRole;
import edu.depaul.cdm.se452.concept.security.UserRoleRepository;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner run(UserRoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
		return args -> {

			log.info("checking adding user");
			if (roleRepo.findByAuthority("ADMIN").isPresent()) return;

			log.info("adding user");
			roleRepo.save(new UserRole("USER"));
			UserRole adminRole = roleRepo.save(new UserRole("ADMIN"));

			Set<UserRole> roles = new HashSet<>();
			roles.add(adminRole);

			AppUser admin = new AppUser("admin", "{bcrypt}" + encoder.encode("password"), roles);

			userRepo.save(admin);
		};
	} 
}
