package edu.depaul.cdm.se452.concept.security;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    @GetMapping("/")
    public String defaultRoot() {
        return "Default admin access level";
    }

}
