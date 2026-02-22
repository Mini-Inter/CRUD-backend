package com.school.miniinter.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashConfig {
    public static String hashSenha(String password) throws NoSuchAlgorithmException {
        Dotenv envVars = Dotenv.load();
        MessageDigest algorithm =
                MessageDigest.getInstance(envVars.get("ALGORITIMO_HASH"));
        byte[] mensagemDigest =
                algorithm.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hashString = new StringBuilder();
        for (byte b : mensagemDigest) {
            hashString.append(String.format("%02X", 0xFF & b));
        }
        return hashString.toString();
    }
}
