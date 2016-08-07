package com.lucaslouca.util;

import com.lucaslouca.model.LLTown;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The {@code  LLTownMapImpl} class implements the {@code  LLTownMap} interface.
 * <p>
 * {@code  LLTownMapImpl} is a map that stores towns using a {@code  LLDirectedGraph} underneath.
 * <p>
 * It wraps the functionality of {@code  LLDirectedGraph} and provides methods for accessing it using the town names.
 */
public class LLTownMapImpl implements LLTownMap {
    /**
     * {@code  Exception} that is thrown when a provided route is not valid because a town or road to
     * that town does not exist.
     */
    public static class NoSuchRouteException extends RuntimeException {
        public NoSuchRouteException(String message) {
            super(message);
        }
    }

    private LLDirectedGraph<LLTown> graph;
    private Map<String, LLTown> towns;

    /**
     * Creates a new {@code  LLTownMapImpl}.
     */
    public LLTownMapImpl() {
        graph = new LLDirectedGraph<LLTown>();
        towns = new HashMap<String, LLTown>();
    }

    /**
     * Add a town to this map.
     *
     * @param town {@code LLTown} to add to this map.
     */
    private void addTown(LLTown town) {
        graph.addNode(town);
        if (!towns.containsKey(town.getName())) {
            towns.put(town.getName(), town);
        }
    }

    /***************************************************************************************************/
    /*                                      PUBLIC METHODS                                             *
    /***************************************************************************************************/

    /**
     * Parse input of the format {@code  XYN} representing a route between two towns {@code  X} and {@code  Y}, with:
     * <p>
     * {@code  X} is a single character.
     * <p>
     * {@code  Y} is a single character.
     * <p>
     * {@code  N} (optional) is an integer {@code  >= 0}, representing the distance between X and Y.
     *
     * @param input line input to parse.
     * @throws IllegalArgumentException if input is not in the above described format.
     */
    @Override
    public void parseInput(String input) {
        if (input.length() < 2) {
            throw new IllegalArgumentException(LLPropertyFactory.getProperties().get("exception.town_map.illegal_argument.parse_input_format"));
        } else {
            String start = new String(String.valueOf(input.charAt(0)));
            String dest = new String(String.valueOf(input.charAt(1)));

            int weight = 0; // default weight is 0.

            if (input.length() > 2) {
                // If route weight is provided, try to parse it.
                try {
                    weight = Integer.parseInt(input.substring(2));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.town_map.illegal_argument.parse_input_weight",input));
                }
            }

            LLTown startTown = new LLTown(start);
            LLTown destTown = new LLTown(dest);

            addTown(startTown);
            addTown(destTown);
            graph.addEdge(startTown, destTown, weight);


        }
    }

    /**
     * Initialise map from a text file at path. Each line in the file is of format {@code  XYN}
     * representing a route between two towns {@code  X} and {@code  Y}, with:
     * <p>
     * {@code  X} is a single character.
     * <p>
     * {@code  Y} is a single character.
     * <p>
     * {@code  N} (optional) is an integer {@code  >= 0}, representing the distance between X and Y.
     * <p>
     * Sample File content:
     * <p>
     * AB5<br>
     * BC4<br>
     * CD8<br>
     * DC8<br>
     * DE6<br>
     * AD5<br>
     * CE2<br>
     * EB3<br>
     * AE7<br>
     *
     * @param path absolute path to the input file.
     * @throws FileNotFoundException if file not found at given path.
     */
    @Override
    public void init(String path) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream(path));

        while (in.hasNext()) {
            String input = in.next();
            parseInput(input);
        }
    }

    /**
     * {@code String} representing the shortest path between start and dest.
     *
     * @param start name of start town.
     * @param dest  name of town which we want to reach.
     * @return {@code String} representing the shortest path between start and dest.
     * @throws NoSuchRouteException if no route exists between the two towns.
     */
    @Override
    public String shortestPathBetween(String start, String dest) throws NoSuchRouteException {
        try {
            LLDirectedGraph.GraphPath path = graph.shortestPathBetween(towns.get(start), towns.get(dest));

            return path.toString();
        } catch (LLDirectedGraph.NodeNotReachableException | NoSuchElementException e) {
            throw new NoSuchRouteException(e.getMessage());
        }
    }

    /**
     * Distance of shortest path between start and dest.
     *
     * @param start name of start town.
     * @param dest  name of town which we want to reach.
     * @return length of the shortest path between start and dest.
     * @throws NoSuchRouteException if no route exists between the two towns.
     */
    @Override
    public int lengthOfShortestPathBetween(String start, String dest) throws NoSuchRouteException {
        try {
            LLDirectedGraph.GraphPath path = graph.shortestPathBetween(towns.get(start), towns.get(dest));

            return path.distance();
        } catch (LLDirectedGraph.NodeNotReachableException | NoSuchElementException e) {
            throw new NoSuchRouteException(e.getMessage());
        }
    }

    /**
     * Distance of route.
     *
     * @param townNames town names in the order as they should be visited.
     * @return length of the route.
     * @throws NoSuchRouteException if no such route exists.
     */
    @Override
    public int distance(String... townNames) throws NoSuchRouteException {
        List<LLTown> townList = new ArrayList<LLTown>();
        for (String townName : townNames) {
            townList.add(towns.get(townName));
        }

        try {
            return graph.distance(townList);
        } catch (NoSuchElementException | LLDirectedGraph.NodeNotReachableException e) {
            throw new NoSuchRouteException(e.getMessage());
        }
    }

    /**
     * Count number of possible routes starting from start and ending at dest with a maximum number of {@code maxHops} .
     *
     * @param start   name of starting town of route.
     * @param dest    name of destination town of route.
     * @param maxHops maximum number of stops in route.
     * @return number of possible routes.
     */
    @Override
    public int countRoutesWithMaxHops(String start, String dest, int maxHops) {
        return graph.countRoutesWithMaxHops(towns.get(start), towns.get(dest), maxHops);
    }

    /**
     * Number of possible routes starting from start and ending at dest with a exactly {@code hops}  number of stops.
     *
     * @param start name of starting town of route.
     * @param dest  name of destination town of route.
     * @param hops  number of stops in route.
     * @return number of possible routes.
     */
    @Override
    public int countRoutesWithHops(String start, String dest, int hops) {
        return graph.countRoutesWithHops(towns.get(start), towns.get(dest), hops);
    }

    /**
     * Number of possible routes starting from start and ending at dest with a maximum distance of {@code maxDistance}.
     *
     * @param start       name of starting town of route.
     * @param dest        name of destination town of route.
     * @param maxDistance maximum allowed distance.
     * @return number of possible routes.
     */
    @Override
    public int countRoutesWithMaxDistance(String start, String dest, int maxDistance) {
        return graph.countRoutesWithMaxDistance(towns.get(start), towns.get(dest), maxDistance);
    }
}
