package edu.pasadena.cs.cs03b;

import java.util.Random;

public class RandomModel {
    private DreamMap map;
    private Random rand;

    // constructor
    public RandomModel(DreamMap map) {
        this.map = map;
        this.rand = new Random();
    }

    // generate with specified starting word
    public String generate(int wordLimit, String start){
        if (map.size() == 0) 
            return "";

        String current = start;
        StringBuilder sb = new StringBuilder(current); // create a string builder to store the generated text

        // iterate until the word limit is reached
        for (int i = 1; i < wordLimit; i++){
            DreamList list = map.get(current); // get the list of next words for the current word
            if (list == null || list.size() == 0) 
                break;

            String next = list.getRandomElement(rand); // get a random next word from the list
            if (next == null) 
                break;

            sb.append(" ").append(next);
            current = next; // update the current word to the next word and keep looping
        }

        return sb.toString();
    }

    // generate with random starting word
    public String generate(int wordLimit){
        if (map.size() == 0) 
            return "";
        return generate(wordLimit, map.getRandomKey());
    }
}
