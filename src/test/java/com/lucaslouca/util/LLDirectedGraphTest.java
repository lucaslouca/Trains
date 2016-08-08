package com.lucaslouca.util;

import com.lucaslouca.model.LLTown;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Tests for {@code LLDirectedGraph}.
 */
public class LLDirectedGraphTest {
    private LLDirectedGraph<LLTown> graph;
    private static Map<String, LLTown> towns;

    @BeforeClass
    public static void initTowns() {
        towns = new HashMap<String, LLTown>();

        towns.put("A", new LLTown("A"));
        towns.put("B", new LLTown("B"));
        towns.put("C", new LLTown("C"));
        towns.put("D", new LLTown("D"));
        towns.put("E", new LLTown("E"));
    }

    @Before
    public void initObjects() {
        graph = new LLDirectedGraph<>();

        LLTown townA = towns.get("A");
        LLTown townB = towns.get("B");
        LLTown townC = towns.get("C");
        LLTown townD = towns.get("D");
        LLTown townE = towns.get("E");

        graph.addNode(townA);
        graph.addNode(townB);
        graph.addNode(townC);
        graph.addNode(townD);
        graph.addNode(townE);

        graph.addEdge(townA, townB, 5);
        graph.addEdge(townB, townC, 4);
        graph.addEdge(townC, townD, 8);
        graph.addEdge(townD, townC, 8);
        graph.addEdge(townD, townE, 6);
        graph.addEdge(townA, townD, 5);
        graph.addEdge(townC, townE, 2);
        graph.addEdge(townE, townB, 3);
        graph.addEdge(townA, townE, 7);
    }

    @Test
    public void testAddNode1() {
        LLDirectedGraph<LLTown> g = new LLDirectedGraph<>();
        LLTown townA = new LLTown("A");
        g.addNode(townA);

        assertTrue(g.contains(townA));
    }

    @Test
    public void testAddEdge1() {
        LLDirectedGraph<LLTown> g = new LLDirectedGraph<>();
        LLTown townA = new LLTown("A");
        g.addNode(townA);

        LLTown townB = new LLTown("B");
        g.addNode(townB);

        g.addEdge(townA, townB, 5);
        assertTrue(g.edgeExists(townA, townB));
    }

    @Test
    public void testGetNeighBours1() {
        LLDirectedGraph<LLTown> g = new LLDirectedGraph<>();
        LLTown townA = new LLTown("A");
        g.addNode(townA);

        LLTown townB = new LLTown("B");
        g.addNode(townB);

        LLTown townC = new LLTown("C");
        g.addNode(townC);

        g.addEdge(townA, townB, 5);
        g.addEdge(townA, townC, 2);

        Set<LLTown> neighbours = g.getNeighbours(townA);
        assertEquals(2, neighbours.size());
    }

    @Test
    public void testGetNeighBours2() {
        LLDirectedGraph<LLTown> g = new LLDirectedGraph<>();
        LLTown townA = new LLTown("A");
        g.addNode(townA);

        LLTown townB = new LLTown("B");
        g.addNode(townB);

        g.addEdge(townA, townB, 5);

        Set<LLTown> neighbours = g.getNeighbours(townA);
        assertTrue(neighbours.contains(townB));
    }

    @Test
    public void testShortestPathBetween1() throws LLDirectedGraph.NodeNotReachableException {
        LLDirectedGraph.GraphPath path;

        path = graph.shortestPathBetween(towns.get("A"), towns.get("B"));
        assertEquals("A->B", path.toString());

        path = graph.shortestPathBetween(towns.get("A"), towns.get("D"));
        assertEquals("A->D", path.toString());

        path = graph.shortestPathBetween(towns.get("A"), towns.get("E"));
        assertEquals("A->E", path.toString());

        path = graph.shortestPathBetween(towns.get("D"), towns.get("E"));
        assertEquals("D->E", path.toString());

        path = graph.shortestPathBetween(towns.get("E"), towns.get("B"));
        assertEquals("E->B", path.toString());

        path = graph.shortestPathBetween(towns.get("B"), towns.get("C"));
        assertEquals("B->C", path.toString());

        path = graph.shortestPathBetween(towns.get("C"), towns.get("E"));
        assertEquals("C->E", path.toString());

        path = graph.shortestPathBetween(towns.get("C"), towns.get("D"));
        assertEquals("C->D", path.toString());

        path = graph.shortestPathBetween(towns.get("D"), towns.get("C"));
        assertEquals("D->C", path.toString());

    }

    @Test
    public void testShortestPathBetween2() throws LLDirectedGraph.NodeNotReachableException {
        LLDirectedGraph.GraphPath path;

        path = graph.shortestPathBetween(towns.get("B"), towns.get("E"));
        assertEquals("B->C->E", path.toString());
        assertEquals(6, path.distance());
        assertEquals(2, path.hopCount());

        path = graph.shortestPathBetween(towns.get("E"), towns.get("C"));
        assertEquals("E->B->C", path.toString());
        assertEquals(7, path.distance());
        assertEquals(2, path.hopCount());

        path = graph.shortestPathBetween(towns.get("D"), towns.get("B"));
        assertEquals("D->E->B", path.toString());
        assertEquals(9, path.distance());
        assertEquals(2, path.hopCount());
    }

    @Test(expected = LLDirectedGraph.NodeNotReachableException.class)
    public void testShortestPathBetween3() throws LLDirectedGraph.NodeNotReachableException {
        graph.shortestPathBetween(towns.get("E"), towns.get("A"));
    }

    @Test(expected = LLDirectedGraph.NodeNotReachableException.class)
    public void testShortestPathBetween4() throws LLDirectedGraph.NodeNotReachableException {
        graph.shortestPathBetween(towns.get("C"), towns.get("A"));
    }


    @Test
    public void testDistance1() throws LLDirectedGraph.NodeNotReachableException {
        List<LLTown> route = new ArrayList<LLTown>();
        route.add(towns.get("A"));
        route.add(towns.get("B"));
        route.add(towns.get("C"));

        int ans = graph.distance(route);
        assertEquals(9, ans);
    }

    @Test
    public void testDistance2() throws LLDirectedGraph.NodeNotReachableException {
        List<LLTown> route = new ArrayList<LLTown>();
        route.add(towns.get("A"));
        route.add(towns.get("D"));
        route.add(towns.get("E"));

        int ans = graph.distance(route);
        assertEquals(11, ans);
    }

    @Test
    public void testDistance3() throws LLDirectedGraph.NodeNotReachableException {
        List<LLTown> route = new ArrayList<LLTown>();
        route.add(towns.get("B"));
        route.add(towns.get("C"));
        route.add(towns.get("D"));
        route.add(towns.get("E"));
        route.add(towns.get("B"));

        int ans = graph.distance(route);
        assertEquals(21, ans);
    }

    @Test
    public void testDistance4() throws LLDirectedGraph.NodeNotReachableException {
        List<LLTown> route = new ArrayList<LLTown>();
        route.add(towns.get("B"));
        route.add(towns.get("C"));
        route.add(towns.get("D"));
        route.add(towns.get("E"));
        route.add(towns.get("B"));
        route.add(towns.get("C"));
        route.add(towns.get("E"));
        route.add(towns.get("B"));

        int ans = graph.distance(route);
        assertEquals(30, ans);
    }

    @Test(expected = LLDirectedGraph.NodeNotReachableException.class)
    public void testDistanceException1() throws LLDirectedGraph.NodeNotReachableException {
        List<LLTown> route = new ArrayList<LLTown>();
        route.add(towns.get("B"));
        route.add(towns.get("B"));

        int ans = graph.distance(route);
    }


    @Test
    public void testCountRoutesWithMaxHops1() {
        int ans = graph.countRoutesWithMaxHops(towns.get("C"), towns.get("C"), 3);
        assertEquals(2, ans);
    }

    @Test
    public void testCountRoutesWithMaxHops2() {
        int ans = graph.countRoutesWithMaxHops(towns.get("A"), towns.get("B"), 3);
        assertEquals(3, ans);
    }

    @Test
    public void testCountRoutesWithMaxHops3() {
        int ans = graph.countRoutesWithMaxHops(towns.get("B"), towns.get("C"), 3);
        assertEquals(2, ans);
    }

    @Test
    public void testCountRoutesWithMaxHops4() {
        int ans = graph.countRoutesWithMaxHops(towns.get("B"), towns.get("C"), 2);
        assertEquals(1, ans);
    }

    @Test
    public void testCountRoutesWithMaxHops5() {
        int ans = graph.countRoutesWithMaxHops(towns.get("B"), towns.get("D"), 1);
        assertEquals(0, ans);
    }

    @Test(expected = NoSuchElementException.class)
    public void testCountRoutesWithMaxHopsException() {
        graph.countRoutesWithMaxHops(towns.get("B"), towns.get("X"), 1);
    }

    @Test
    public void testCountRoutesWithHops1() {
        int ans = graph.countRoutesWithHops(towns.get("B"), towns.get("D"), 1);
        assertEquals(0, ans);
    }

    @Test
    public void testCountRoutesWithHops2() {
        int ans = graph.countRoutesWithHops(towns.get("B"), towns.get("D"), 2);
        assertEquals(1, ans);
    }

    @Test
    public void testCountRoutesWithHops3() {
        int ans = graph.countRoutesWithHops(towns.get("B"), towns.get("C"), 1);
        assertEquals(1, ans);
    }

    @Test
    public void testCountRoutesWithHops4() {
        int ans = graph.countRoutesWithHops(towns.get("A"), towns.get("C"), 1);
        assertEquals(0, ans);
    }

    @Test(expected = NoSuchElementException.class)
    public void testCountRoutesWithHopsException() {
        graph.countRoutesWithHops(towns.get("B"), towns.get("X"), 1);
    }

    @Test
    public void testCountRoutesWithMaxDistance1() {
        int ans = graph.countRoutesWithMaxDistance(towns.get("B"), towns.get("D"), 1);
        assertEquals(0, ans);
    }

    @Test
    public void testCountRoutesWithMaxDistance2() {
        int ans = graph.countRoutesWithMaxDistance(towns.get("B"), towns.get("E"), 6);
        assertEquals(1, ans);
    }

    @Test
    public void testCountRoutesWithMaxDistance3() {
        int ans = graph.countRoutesWithMaxDistance(towns.get("B"), towns.get("E"), 18);
        assertEquals(3, ans);
    }

    @Test
    public void testCountRoutesWithMaxDistance4() {
        int ans = graph.countRoutesWithMaxDistance(towns.get("A"), towns.get("E"), 7);
        assertEquals(1, ans);
    }

    @Test
    public void testCountRoutesWithMaxDistance5() {
        int ans = graph.countRoutesWithMaxDistance(towns.get("C"), towns.get("D"), 32);
        assertEquals(5, ans);
    }

    @Test(expected = NoSuchElementException.class)
    public void testCountRoutesWithMaxDistanceExcpetion() {
        graph.countRoutesWithMaxDistance(towns.get("C"), towns.get("X"), 2);
    }
}
