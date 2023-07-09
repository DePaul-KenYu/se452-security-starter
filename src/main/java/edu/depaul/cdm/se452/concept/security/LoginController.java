package edu.depaul.cdm.se452.concept.security;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/login")
@Profile("non-jwt")
@AllArgsConstructor
public class LoginController {

    private AuthenticationService authenticationService;

    @PostMapping("/")
    public AppUser login(@RequestBody AppUser user) {
        return authenticationService.loginUser(user.getUsername(), user.getPassword());
    }
    
}
