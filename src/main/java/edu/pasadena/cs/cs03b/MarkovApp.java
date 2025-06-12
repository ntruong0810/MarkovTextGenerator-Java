package edu.pasadena.cs.cs03b;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MarkovApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // ask for filename
        System.out.print("Enter input filename: ");
        String filename = input.nextLine().trim();
        
        DreamMap map = new DreamMap(100);
        buildMarkovChain(filename, map);

        // ask for words limit
        // int wordLimit = 100;
        System.out.print("Enter number of words to generate: ");
        int wordLimit = Integer.parseInt(input.nextLine().trim());

        // ask if user wants to specify starting word
        System.out.print("Do you want to specify a starting word? (y/n): ");
        String choice = input.nextLine().trim().toLowerCase();

        String startWord;
        if(choice.equals("y")){
            System.out.print("Enter starting word: ");
            startWord = input.nextLine().trim().toLowerCase();
        } 
        else 
        {
            startWord = map.getRandomKey();
            System.out.println("Randomly selected starting word: " + startWord);
        }

        RandomModel randomModel = new RandomModel(map);
        String randomText = randomModel.generate(wordLimit);

        WeightedModel weightedModel = new WeightedModel(map);
        String weightedText = weightedModel.generate(wordLimit, startWord);

        POMDPModel pomdpModel = new POMDPModel(map, 0.8);
        String pomdpText = pomdpModel.generate(wordLimit, startWord);

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
