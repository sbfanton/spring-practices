package com.course.springsecuritycourse.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomKeyGen {
    
    public static String generateRandomKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretKeyBytes = new byte[32];
        secureRandom.nextBytes(secretKeyBytes);
        return Base64.getEncoder().encodeToString(secretKeyBytes);
    }
}
