package edu.pasadena.cs.cs03b;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MarkovApp {
    public static void main(String[] args) {
        String filename = "src/Speeches/Speech_on_Economic_Mobility_Obama.txt"; 
        DreamMap map = new DreamMap(100);
        buildMarkovChain(filename, map);

        int wordLimit = 100;

        RandomModel randomModel = new RandomModel(map);
        String randomText = randomModel.generate(wordLimit);

        WeightedModel weightedModel = new WeightedModel(map);
        String weightedText = weightedModel.generate(wordLimit);

        POMDPModel pomdpModel = new POMDPModel(map, 0.8);
        String pomdpText = pomdpModel.generate(wordLimit);

        System.out.println("Random Model Text Generator:");
        System.out.println(randomText +"\n");

        System.out.println("Probabilistic Model Text Generator:");
        System.out.println(weightedText +"\n");

        System.out.println("POMDP Model Text Generator:");
        System.out.println(pomdpText +"\n");
    }

    private static void buildMarkovChain(String filename, DreamMap map) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            String prev = null;

            while (scanner.hasNext()){
                String rawToken = scanner.next();
                // converting to lowercase
                String lowercaseToken = rawToken.toLowerCase();

                // removing non-alphabetic characters
                StringBuilder cleaned = new StringBuilder();
                for(char c : lowercaseToken.toCharArray()){
                    if(c >= 'a' && c <= 'z'){
                        cleaned.append(c);
                    }
                }
                
                String word = cleaned.toString();
                if (word.isEmpty()) 
                    continue;

                if (prev != null){
                    DreamList list = map.get(prev);
                    if (list == null) 
                    {
                        list = new DreamList(2);
                        map.put(prev, list);
                    }
                    list.add(word);
                }

                prev = word;
            }

            scanner.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("Error: File not found " + filename);
        }
    }
}
