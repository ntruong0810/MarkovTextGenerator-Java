package edu.pasadena.cs.cs03b;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class TestDreamMap {

    @Test
    public void testPutAndGet() {
        DreamMap map = new DreamMap(1);
        DreamList list = new DreamList(1);
        list.add("one");
        map.put("key", list);
        assertEquals(1, map.size());
        assertTrue(map.containsKey("key"));
        DreamList ret = map.get("key");
        assertNotNull(ret);
        assertEquals("one", ret.get(0));
    }

    @Test
    public void testPutAppend() {
        DreamMap map = new DreamMap(1);
        DreamList first = new DreamList(1);
        first.add("a");
        map.put("k", first);
        DreamList second = new DreamList(1);
        second.add("b");
        map.put("k", second);
        DreamList got = map.get("k");
        assertEquals(2, got.size());
        assertEquals("a", got.get(0));
        assertEquals("b", got.get(1));
    }

    @Test
    public void testPutIfAbsent() {
        DreamMap map = new DreamMap(1);
        DreamList l1 = new DreamList(1);
        l1.add("x");
        map.putIfAbsent("z", l1);
        DreamList l2 = new DreamList(1);
        l2.add("y");
        map.putIfAbsent("z", l2);
        DreamList got = map.get("z");
        assertEquals(1, got.size());
        assertEquals("x", got.get(0));
    }

    @Test
    public void testKeys() {
        DreamMap map = new DreamMap(2);
        map.put("a", new DreamList(1));
        map.put("b", new DreamList(1));
        String[] keys = map.keys();
        // System.out.println(Arrays.toString(keys));
        assertArrayEquals(new String[]{"a","b"}, keys);
    }
}
