package edu.depaul.cdm.se452.concept.security;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
@Profile("non-jwt")
public class SelfRegistrationController {
    private AuthenticationService authService;
    
    @PostMapping("/")
    public AppUser register(@RequestBody AppUser user) {
        return authService.registerUser(user.getUsername(), user.getPassword());
    }   
}
