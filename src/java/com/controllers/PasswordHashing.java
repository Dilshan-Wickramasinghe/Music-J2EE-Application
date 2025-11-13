/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author Udara
 */
public class PasswordHashing {
     public static String hashPassword(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] hashedBytes = md.digest(password.getBytes());

            byte[] combined = new byte[hashedBytes.length + salt.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedBytes, 0, combined, salt.length, hashedBytes.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        try {
            byte[] combined = Base64.getDecoder().decode(hashedPassword);
            byte[] salt = Arrays.copyOfRange(combined, 0, 16);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] hashedBytes = md.digest(password.getBytes());
            byte[] storedHash = Arrays.copyOfRange(combined, 16, combined.length);
            return Arrays.equals(hashedBytes, storedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
}
