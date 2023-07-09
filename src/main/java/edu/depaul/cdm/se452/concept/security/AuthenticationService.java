package edu.depaul.cdm.se452.concept.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
@Profile("non-jwt")
public class AuthenticationService {

    private UserRepository userRepo;
    
    private UserRoleRepository userRoleRepo;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authManager;

    public AppUser registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        UserRole userRole = userRoleRepo.findByAuthority("USER").get();

        Set<UserRole> authorities = new HashSet<>();
        authorities.add(userRole);
        return userRepo.save(new AppUser(username, "{bcrypt}" + encodedPassword, authorities));
    }
    

    public AppUser loginUser(String username, String password) {
        AppUser retval = new AppUser();

        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        retval = userRepo.findByUsername(username).get();

        return retval;
    }

}
