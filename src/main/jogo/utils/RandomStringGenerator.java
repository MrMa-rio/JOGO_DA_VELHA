package src.main.jogo.utils;

import java.util.Random;

public class RandomStringGenerator {


    public static String random() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        Random random = new Random();
        StringBuilder result = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }
}
