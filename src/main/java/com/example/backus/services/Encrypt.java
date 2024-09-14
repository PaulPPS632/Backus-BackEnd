package com.example.backus.services;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encrypt {
    private static final String HMAC_SHA256 = "HmacSHA256";

    // Definir la clave privada
    private static final String SECRET_KEY = "my-secret-key"; // Cambia esto por tu clave privada

    public static String hashPassword(String password) {
        try {
            Mac sha256Hmac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), HMAC_SHA256);
            sha256Hmac.init(secretKeySpec);

            // Generar el hash
            byte[] hashedPassword = sha256Hmac.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword); // Codificar el hash en base64
        } catch (Exception e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }
}
