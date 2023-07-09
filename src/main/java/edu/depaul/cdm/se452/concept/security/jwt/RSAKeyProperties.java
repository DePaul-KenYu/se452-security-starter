package edu.depaul.cdm.se452.concept.security.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class RSAKeyProperties {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    private static KeyPair KEY_PAIR;

    static {
        try {
            var kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KEY_PAIR = kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public RSAKeyProperties() {
        this.publicKey = (RSAPublicKey) KEY_PAIR.getPublic();
        this.privateKey = (RSAPrivateKey) KEY_PAIR.getPrivate();
    }    
}
