package edu.depaul.cdm.se452.concept.security.jwt;

import edu.depaul.cdm.se452.concept.security.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private AppUser user;
    private String jwt;    
}
