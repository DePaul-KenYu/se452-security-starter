package edu.depaul.cdm.se452.concept.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Profile("user-hardcoded")
@AllArgsConstructor
public class UserService implements UserDetailsService{

    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("In the user detail service");

        if (!"ken".equals(username)) throw new UsernameNotFoundException("Not Ken");

        Set<UserRole> roles = new HashSet<>();
        roles.add(new UserRole("USER"));

        return new AppUser("ken", encoder.encode("password"), roles);
        
    }
    
}
