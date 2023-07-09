package edu.depaul.cdm.se452.concept.security.jwt;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.depaul.cdm.se452.concept.security.AppUser;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/login")
@Profile("jwt")
@AllArgsConstructor
public class LoginController {

    private AuthenticationService authenticationService;

    @PostMapping("/")
    public LoginResponse login(@RequestBody AppUser user) {
        LoginResponse retval;

        retval = authenticationService.loginUser(user.getUsername(), user.getPassword());
        
        return retval;
    }
    
}
