package com.lucaslouca.util;

import com.lucaslouca.model.LLTown;
import com.lucaslouca.util.LLDirectedGraph;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by lucas on 07/08/16.
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
    public void testShortestPathBetween5() {

    }

    @Test
    public void testDistance1() {

    }

    @Test
    public void testDistance2() {

    }

    @Test
    public void testDistance3() {

    }

    @Test
    public void testDistance4() {

    }

    @Test
    public void testDistance5() {

    }

    @Test
    public void testCountRoutesWithMaxHops1() {

    }

    @Test
    public void testCountRoutesWithMaxHops2() {

    }

    @Test
    public void testCountRoutesWithMaxHops3() {

    }

    @Test
    public void testCountRoutesWithMaxHops4() {

    }

    @Test
    public void testCountRoutesWithMaxHops5() {

    }

    @Test
    public void testCountRoutesWithHops1() {

    }

    @Test
    public void testCountRoutesWithHops2() {

    }

    @Test
    public void testCountRoutesWithHops3() {

    }

    @Test
    public void testCountRoutesWithHops4() {

    }

    @Test
    public void testCountRoutesWithHops5() {

    }

    @Test
    public void testCountRoutesWithMaxDistance1() {

    }

    @Test
    public void testCountRoutesWithMaxDistance2() {

    }

    @Test
    public void testCountRoutesWithMaxDistance3() {

    }

    @Test
    public void testCountRoutesWithMaxDistance4() {

    }

    @Test
    public void testCountRoutesWithMaxDistance5() {

    }
}
