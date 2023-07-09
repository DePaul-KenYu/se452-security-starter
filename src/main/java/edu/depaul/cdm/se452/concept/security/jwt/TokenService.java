package edu.depaul.cdm.se452.concept.security.jwt;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Profile("jwt")
public class TokenService {
    private JwtEncoder jwtEncoder;
    
    public String generateJwt(Authentication auth) {
        String retval;

        // get all the authorities into a single string
        String scope = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(Instant.now())
            .subject((auth.getName()))
            .claim("roles", scope)
            .build();

        retval = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        
        return retval;
    }
}
