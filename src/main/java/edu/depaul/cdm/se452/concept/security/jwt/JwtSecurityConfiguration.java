package edu.depaul.cdm.se452.concept.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@Profile("jwt")
public class JwtSecurityConfiguration {
    
    private final RSAKeyProperties keys;

    public JwtSecurityConfiguration(RSAKeyProperties keys) {
        this.keys = keys;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        return http
        .csrf(csrf -> csrf.disable())        
        .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/register/**", "/login/**").permitAll();
            auth.requestMatchers("/admin/**").hasRole("ADMIN");
            auth.requestMatchers("/user/**").hasAnyRole("ADMIN", "USER");            
            auth.anyRequest().authenticated();
        })
        .oauth2ResourceServer(oauth2 -> {
            oauth2.jwt(Customizer.withDefaults());
            oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter()));
        })                
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();

        
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder retval = new BCryptPasswordEncoder();
        return retval;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoProvider);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGAC = new JwtGrantedAuthoritiesConverter();
        jwtGAC.setAuthoritiesClaimName("roles");
        jwtGAC.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAC = new JwtAuthenticationConverter();
        jwtAC.setJwtGrantedAuthoritiesConverter(jwtGAC);
        return jwtAC;
    }
}
