package com.hotel.util;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordHashTest {
    public static void main(String[] args) throws Exception {
        String password = "Admin@123";

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b & 0xff));
        }

        System.out.println("Password hash: " + hex);
    }
}
