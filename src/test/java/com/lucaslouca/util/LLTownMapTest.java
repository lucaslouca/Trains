package com.lucaslouca.util;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@code LLTownMap}.
 */
public class LLTownMapTest {
    private LLTownMap map;

    @Before
    public void initObjects() {
        map = new LLTownMapImpl();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("graph.txt").getFile());
            map.init(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParseInput1() {
        int ans = map.lengthOfShortestPathBetween("A", "C");
        assertEquals(9, ans);

        map.parseInput("AC1");
        ans = map.lengthOfShortestPathBetween("A", "C");
        assertEquals(1, ans);
    }

    @Test
    public void testParseInput2() {
        int ans = map.countRoutesWithHops("A", "C", 4);
        assertEquals(3, ans);

        map.parseInput("AC1");
        ans = map.countRoutesWithHops("A", "C", 4);
        assertEquals(4, ans);
    }

    @Test
    public void testParseInput3() {
        int ans = map.lengthOfShortestPathBetween("A", "C");
        assertEquals(9, ans);

        map.parseInput("AC");
        ans = map.lengthOfShortestPathBetween("A", "C");
        assertEquals(0, ans);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseInputException1() {
        map.parseInput("ACC1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseInputException2() {
        map.parseInput("A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseInputException3() {
        map.parseInput("ACX");
    }
}
