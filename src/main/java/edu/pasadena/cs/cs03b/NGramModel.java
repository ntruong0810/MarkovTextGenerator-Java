package edu.pasadena.cs.cs03b;

import java.util.*;

public class NGramModel {
    private DreamMap map;
    private int n;
    private Random rand;

    public NGramModel(int n) {
        if (n < 2) throw new IllegalArgumentException("n must be >= 2");
        this.n = n;
        this.map = new DreamMap(500);
        this.rand = new Random();
    }

    public void train(List<String> words) {
        for (int i = 0; i <= words.size() - n; i++) {
            // Build key from (n-1) words
            StringBuilder keyBuilder = new StringBuilder();
            // Append the first (n-1) words to the key
            for (int j = 0; j < n - 1; j++) {
                keyBuilder.append(words.get(i + j));
                if (j < n - 2) keyBuilder.append(" ");
            }
            String key = keyBuilder.toString();
            String nextWord = words.get(i + n - 1);

            DreamList list = map.get(key);
            if (list == null) {
                list = new DreamList(2);
                map.put(key, list);
            }
            list.add(nextWord);
        }
    }
    
    // Generate a sequence of words with a random starting phrase
    public String generate(int wordLimit) {
    String[] keys = map.keys();
    if (keys.length == 0) return "";

    // Randomly select a starting key from the map
    String startKey = keys[rand.nextInt(keys.length)];
    return generate(wordLimit, startKey);
    }

    // Generate a sequence of words starting from a given phrase
    public String generate(int wordLimit, String startingPhrase) {
    if (startingPhrase == null || startingPhrase.isEmpty()) {
        return generate(wordLimit); // fallback nếu không có input
    }

    String[] initialWords = startingPhrase.trim().toLowerCase().split("\\s+");
    List<String> current = new ArrayList<>();

    if (initialWords.length < n - 1) {
        return "Starting phrase must contain at least " + (n - 1) + " words.";
    }

    for (int i = initialWords.length - (n - 1); i < initialWords.length; i++) {
        current.add(initialWords[i]);
    }

    StringBuilder sb = new StringBuilder(String.join(" ", current));

    for (int i = n - 1; i < wordLimit; i++) {
        String key = String.join(" ", current);
        DreamList nextWords = map.get(key);
        if (nextWords == null || nextWords.size() == 0) break;

        String next = nextWords.getRandomElement(rand);
        if (next == null) break;

        sb.append(" ").append(next);
        current.remove(0);
        current.add(next);
    }

    return sb.toString();
    }
}
