package model.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The type Password encryption util.
 */
public class PasswordEncryptionUtil {

    /**
     * Encrypt password string.
     *
     * @param password the password
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();

        for (byte b : bytes) {
            String hex = String.format("%02x", b);
            hexStringBuilder.append(hex);
        }

        return hexStringBuilder.toString();
    }


}
