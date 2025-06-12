package edu.pasadena.cs.cs03b;

import java.util.Random;

public class POMDPModel {
    private DreamMap map;
    private double probabilityOfAccuracy;
    private Random rand;

    public POMDPModel(DreamMap map, double probabilityOfAccuracy){
        this.map = map;
        this.probabilityOfAccuracy = probabilityOfAccuracy;
        this.rand = new Random();
    }

    // Generate a sequence of words using the POMDP logic
    public String generate(int wordLimit, String start){
        if (map.size() == 0) 
            return ""; 

        StringBuilder sb = new StringBuilder();
        String current = start;
        sb.append(current);

        for (int i = 1; i < wordLimit; i++){
            DreamList list = map.get(current); // Get the possible next words for current
            if (list == null || list.size() == 0) 
                break;

            String next;
            double randomValue = rand.nextDouble(); // randomly generate number between 0.0 and 1.0

            if(randomValue < probabilityOfAccuracy) // compare randomValue with probabilityOfAccuracy
                next = list.getWeightedRandomElement(rand);
            else 
                next = list.getRandomElement(rand);

            if (next == null) break;

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
