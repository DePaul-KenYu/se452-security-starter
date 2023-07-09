package edu.depaul.cdm.se452.concept.security;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @GetMapping("/")
    public String defaultRoot() {
        return "Default User access level";
    }
}
