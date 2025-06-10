package edu.pasadena.cs.cs03b;

import java.util.Random;

public class WeightedModel {
    private DreamMap map;
    private Random rand;

    // constructor
    public WeightedModel(DreamMap map){
        this.map = map;
        this.rand = new Random();
    }

    // generate with specified starting word
    public String generate(int wordLimit, String start){
        if (map.size() == 0) 
            return "";

        StringBuilder sb = new StringBuilder(); // create a string builder to store the generated text
        String current = start; // initialize the current word to the starting word
        sb.append(current);
        // iterate until the word limit 
        for(int i = 1; i < wordLimit; i++){
            DreamList list = map.get(current); // get the list of next words for the current word
            if (list == null || list.size() == 0) 
                break;

            String next = list.getWeightedRandomElement(rand); // get a weighted random next word from the list
            if (next == null) 
                break;

            sb.append(" ").append(next);
            current = next;
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
