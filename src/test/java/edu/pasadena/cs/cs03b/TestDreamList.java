package edu.pasadena.cs.cs03b;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
public class TestDreamList {

    @Test
    public void testAddAndCount() {
        DreamList list = new DreamList(1);
        list.add("hello");
        assertEquals(1, list.size());
        assertEquals(1, list.getCount(0));
        list.add("hello");
        assertEquals(1, list.size());
        assertEquals(2, list.getCount(0));
    }

    @Test
    public void testRemove() {
        DreamList list = new DreamList(1);
        list.add("x");
        list.add("x");
        list.remove(0);
        assertEquals(1, list.size());
        assertEquals(1, list.getCount(0));
        list.remove(0);
        assertEquals(0, list.size());
    }

    @Test
    public void testToArrayAndGet() {
        DreamList list = new DreamList(2);
        list.add("a");
        list.add("b");
        String[] arr = list.toArray();
        assertArrayEquals(new String[]{"a", "b"}, arr);
        assertEquals("b", list.get(1));
    }

    @Test
    public void testGetTotalCount(){
        DreamList list = new DreamList(5);
        list.add("apple");
        list.add("banana");
        list.add("apple");
        list.add("banana");
        list.add("banana");

        // apple: 2, banana: 3 → total = 5
        assertEquals(5, list.getTotalCount());
    }

    @Test
    public void testGetRandomElement(){
        DreamList list = new DreamList(3);
        list.add("dog");
        list.add("cat");
        list.add("mouse");

        Random rand = new Random(42); // fix seed for predictable output
        String randomWord = list.getRandomElement(rand);
        assertNotNull(randomWord);
        assertTrue(randomWord.equals("cat") || randomWord.equals("dog") || randomWord.equals("mouse"));
    }

    @Test
    public void testGetWeightedRandomElement() {
        DreamList list = new DreamList(3);
        list.add("red");    // count = 1
        list.add("blue");   // count = 1
        list.add("green");  // count = 1
        list.add("green");  // count = 2
        list.add("green");  // count = 3

        // green has 3/5 chance
        Random rand = new Random(1); // fixed seed
        String word = list.getWeightedRandomElement(rand);

        // We don't test for exact result here, just check it's one of the options and not null
        assertNotNull(word);
        assertTrue(word.equals("red") || word.equals("blue") || word.equals("green"));
    }
}
