package edu.depaul.cdm.se452.concept.security.jwt;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.cdm.se452.concept.security.AppUser;
import edu.depaul.cdm.se452.concept.security.UserRepository;
import edu.depaul.cdm.se452.concept.security.UserRole;
import edu.depaul.cdm.se452.concept.security.UserRoleRepository;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
@Profile("jwt")
public class AuthenticationService {

    private UserRepository userRepo;
    
    private UserRoleRepository userRoleRepo;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authManager;

    private TokenService tokenService;

    public AppUser registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        UserRole userRole = userRoleRepo.findByAuthority("USER").get();

        Set<UserRole> authorities = new HashSet<>();
        authorities.add(userRole);
        return userRepo.save(new AppUser(username, "{bcrypt}" + encodedPassword, authorities));
    }
    

    public LoginResponse loginUser(String username, String password) {
        LoginResponse retval = new LoginResponse();
        
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        retval.setUser(userRepo.findByUsername(username).get());
        retval.setJwt(tokenService.generateJwt(auth));

        return retval;
    }

}
