//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package model;

import java.security.SecureRandom;

public class RandomStringGenerator {
    private static final String SYMBOLS = "@$!%*?&";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String ALL_CHARACTERS = "@$!%*?&ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public RandomStringGenerator() {
    }

    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        int length = random.nextInt(17) + 8;
        StringBuilder sb = new StringBuilder(length);
        sb.append(getRandomCharacter("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        sb.append(getRandomCharacter("abcdefghijklmnopqrstuvwxyz"));
        sb.append(getRandomCharacter("@$!%*?&"));
        sb.append(getRandomCharacter("0123456789"));

        for(int i = 4; i < length; ++i) {
            sb.append(getRandomCharacter("@$!%*?&ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"));
        }

        return sb.toString();
    }

    private static char getRandomCharacter(String characters) {
        SecureRandom random = new SecureRandom();
        int index = random.nextInt(characters.length());
        return characters.charAt(index);
    }
}
