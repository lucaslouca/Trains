package com.lucaslouca.service;

import com.lucaslouca.service.LLRailRoadService;
import com.lucaslouca.service.LLRailRoadServiceException;
import com.lucaslouca.service.LLRailRoadServiceImpl;
import com.lucaslouca.util.LLTownMap;
import com.lucaslouca.util.LLTownMapImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the LLRailRoadService implementation
 */
public class LLRailRoadServiceTest {
    private LLRailRoadService service;

    @Before
    public void initObjects() {
        LLTownMap map = new LLTownMapImpl();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("graph.txt").getFile());
            map.init(file.getAbsolutePath());
            service = new LLRailRoadServiceImpl(map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDistance1() {
        int ans = service.distance("A", "B", "C");
        assertEquals(9, ans);
    }

    @Test
    public void testDistance2() {
        int ans = service.distance("A", "D");
        assertEquals(5, ans);
    }

    @Test
    public void testDistance3() {
        int ans = service.distance("A", "D", "C");
        assertEquals(13, ans);
    }

    @Test
    public void testDistance4() {
        int ans = service.distance("A", "E", "B", "C", "D");
        assertEquals(22, ans);
    }

    @Test(expected = LLRailRoadServiceException.class)
    public void testDistanceException() {
        int ans = service.distance("A", "E", "D");
    }

    @Test
    public void testCountRoutesWithMaxHops1() {
        int ans = service.countRoutesWithMaxHops("C", "C", 3);
        assertEquals(2, ans);
    }

    @Test
    public void testCountRoutesWithHops1() {
        int ans = service.countRoutesWithHops("A", "C", 4);
        assertEquals(3, ans);
    }

    @Test
    public void testLengthOfShortestPathBetween1() {
        int ans = service.lengthOfShortestPathBetween("A", "C");
        assertEquals(9, ans);
    }

    @Test
    public void testLengthOfShortestPathBetween2() {
        int ans = service.lengthOfShortestPathBetween("B", "B");
        assertEquals(9, ans);
    }

    @Test
    public void testCountRoutesWithMaxDistance1() {
        int ans = service.countRoutesWithMaxDistance("C", "C", 29);
        assertEquals(7, ans);
    }

    @Test
    public void testShortestPathBetween1() {
        String ans = service.shortestPathBetween("B", "B");
        assertEquals("B->C->E->B", ans);
    }
}
