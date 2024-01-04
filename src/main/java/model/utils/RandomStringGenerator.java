package model.utils;

import java.security.SecureRandom;

public class RandomStringGenerator {
    private static final String SYMBOLS = "@$!%*?&";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String ALL_CHARACTERS = SYMBOLS + UPPERCASE_LETTERS + LOWERCASE_LETTERS + NUMBERS;

    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        int length = random.nextInt(17) + 8; // Genera una lunghezza casuale tra 8 e 24 caratteri
        StringBuilder sb = new StringBuilder(length);

        sb.append(getRandomCharacter(UPPERCASE_LETTERS)); // Aggiunge un carattere maiuscolo
        sb.append(getRandomCharacter(LOWERCASE_LETTERS)); // Aggiunge un carattere minuscolo
        sb.append(getRandomCharacter(SYMBOLS)); // Aggiunge un simbolo
        sb.append(getRandomCharacter(NUMBERS)); // Aggiunge un numero

        for (int i = 4; i < length; i++) {
            sb.append(getRandomCharacter(ALL_CHARACTERS)); // Aggiunge i caratteri rimanenti
        }

        return sb.toString();
    }

    private static char getRandomCharacter(String characters) {
        SecureRandom random = new SecureRandom();
        int index = random.nextInt(characters.length());
        return characters.charAt(index);
    }
}