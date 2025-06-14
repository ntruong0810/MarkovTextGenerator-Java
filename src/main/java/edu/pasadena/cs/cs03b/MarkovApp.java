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

        // Prefix the speeches directory path
        String fullPath = "src/Speeches/" + filename;

        File file = new File(fullPath);
        if (!file.exists()) {
            System.out.println("Error: File not found " + file.getPath());
            input.close();
            return;
        }

        DreamMap map = new DreamMap(100);
        buildMarkovChain(fullPath, map);  // <-- pass the full path here

        // ask for words limit
        System.out.print("Enter number of words to generate: ");
        int wordLimit = Integer.parseInt(input.nextLine().trim());

        // ask if user wants to specify starting word
        System.out.print("Do you want to specify a starting word? (y/n): ");
        String choice = input.nextLine().trim().toLowerCase();

        String startWord;
        if (choice.equals("y")) {
            System.out.print("Enter starting word: ");
            startWord = input.nextLine().trim().toLowerCase();
        } else {
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
        System.out.println(randomText + "\n");

        System.out.println("Probabilistic Model Text Generator:");
        System.out.println(weightedText + "\n");

        System.out.println("POMDP Model Text Generator:");
        System.out.println(pomdpText + "\n");

        System.out.print("Do you want to use an N-Gram Model? (y/n): ");
        String useNGram = input.nextLine().trim().toLowerCase();

        if (useNGram.equals("y")) {
        System.out.print("Enter value of n (e.g., 2 or 3): ");
        int n = Integer.parseInt(input.nextLine().trim());
        if (n < 2) {
            System.out.println("n must be at least 2. Using default n = 2.");
            n = 2;
        }

        NGramModel ngramModel = new NGramModel(n);
        ngramModel.train(tokenizeFile(fullPath));

        // Ask if the user wants to specify a starting phrase
        System.out.print("Do you want to specify a starting word or phrase? (y/n): ");
        String specifyStart = input.nextLine().trim().toLowerCase();

        String ngramStart = null;
        if (specifyStart.equals("y")) {
            System.out.print("Enter starting word or phrase: ");
            ngramStart = input.nextLine().trim().toLowerCase();
        }

        // if ngramStart is null or empty, use a random starting phrase
        // otherwise, use the specified starting phrase
        String ngramText = (ngramStart != null && !ngramStart.isEmpty())
            ? ngramModel.generate(wordLimit, ngramStart)
            : ngramModel.generate(wordLimit);

        System.out.println("N-Gram Model (n = " + n + ") Text Generator:");
        System.out.println(ngramText + "\n");
        }

        input.close();
    }

    private static java.util.List<String> tokenizeFile(String filepath) {
        java.util.List<String> tokens = new java.util.ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filepath));
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase().replaceAll("[^a-z]", "");
                if (!word.isEmpty()) {
                tokens.add(word);
                }
            }
            scanner.close();
        } 
        catch (FileNotFoundException e) 
            System.err.println("Error reading file for NGramModel: " + filepath);
        
        return tokens;
        }

    private static void buildMarkovChain(String fullPath, DreamMap map) {
        File file = new File(fullPath);

        if (!file.exists()) {
            System.err.println("Error: File not found -> " + fullPath);
            System.err.println("Looking for fallback file in 'Speeches/' directory...");

            File fallbackDir = new File("Speeches");
            File[] txtFiles = fallbackDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

            if (txtFiles == null || txtFiles.length == 0) {
                System.err.println("Cant open file");
                System.exit(1);
            }

            file = txtFiles[0];
            System.out.println("Using fallback file: " + file.getName());
        }

        try {
            Scanner scanner = new Scanner(file);
            String prev = null;

            while (scanner.hasNext()) {
                String rawToken = scanner.next();
                String lowercaseToken = rawToken.toLowerCase();

                StringBuilder cleaned = new StringBuilder();
                for (char c : lowercaseToken.toCharArray()) {
                    if (c >= 'a' && c <= 'z') {
                        cleaned.append(c);
                    }
                }

                String word = cleaned.toString();
                if (word.isEmpty())
                    continue;

                if (prev != null) {
                    DreamList list = map.get(prev);
                    if (list == null) {
                        list = new DreamList(2);
                        map.put(prev, list);
                    }
                    list.add(word);
                }

                prev = word;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Can not open file");
            System.exit(1);
        }
    }
}
