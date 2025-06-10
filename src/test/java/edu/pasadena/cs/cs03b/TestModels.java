package edu.pasadena.cs.cs03b;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestModels {

    @Test
    public void testRandomModelEmpty() {
        RandomModel rm = new RandomModel(new DreamMap(1));
        assertEquals("", rm.generate(5));
    }

    @Test
    public void testWeightedModelEmpty() {
        WeightedModel wm = new WeightedModel(new DreamMap(1));
        assertEquals("", wm.generate(5));
    }

    @Test
    public void testRandomModelWithStart() {
        DreamMap map = new DreamMap(2);
        DreamList l = new DreamList(1);
        l.add("foo");
        map.put("start", l);
        RandomModel rm = new RandomModel(map);
        String out = rm.generate(3, "start");
        // always starts with start
        assertTrue(out.startsWith("start"));
    }

    @Test
    public void testWeightedModelWithStart(){
        DreamMap map = new DreamMap(2);
        DreamList l = new DreamList(1);
        l.add("bar");
        map.put("begin", l);
        WeightedModel wm = new WeightedModel(map);
        String out = wm.generate(3, "begin");
        assertTrue(out.startsWith("begin"));
    }

    @Test
    public void testRandomModelSingleWord() {
        DreamMap map = new DreamMap(1);
        DreamList l = new DreamList(1);
        l.add("calculator");
        map.put("calculator", l);
        RandomModel rm = new RandomModel(map);
        String out = rm.generate(1);
        assertEquals("calculator", out);
    }

    @Test
    public void testWeightedModelSingleWord() {
        DreamMap map = new DreamMap(1);
        DreamList l = new DreamList(1);
        l.add("solo");
        map.put("solo", l);
        WeightedModel wm = new WeightedModel(map);
        String out = wm.generate(1);
        assertEquals("solo", out);
    }

}
