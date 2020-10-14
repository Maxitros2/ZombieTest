package me.maxitros.zombie.api;

import java.util.Random;

public class NameGenerator
{
    public static String generateRandomWord(int bound)
    {
        Random random = new Random();
        char[] word = new char[bound]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for(int j = 0; j < word.length; j++)
        {
                word[j] = (char)('a' + random.nextInt(26));
        }
        return new String(word);
    }
}
