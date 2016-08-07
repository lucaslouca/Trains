package com.lucaslouca.service;

import com.lucaslouca.util.LLTownMapImpl;

/**
 * {@code Interface} defining a rail road service system.
 */
public interface LLRailRoadService {
    /**
     * {@code String} representing the shortest path between start and dest.
     *
     * @param start start town.
     * @param dest  town which we want to reach.
     * @return {@code String} representing the shortest path between start and dest.
     * @throws LLTownMapImpl.NoSuchRouteException if no route exists between the two towns.
     */
    String shortestPathBetween(String start, String dest);

    /**
     * Distance of shortest path between start and dest.
     *
     * @param start name of start town.
     * @param dest  name of town which we want to reach.
     * @return length of the shortest path between start and dest.
     * @throws LLTownMapImpl.NoSuchRouteException if no route exists between the two towns.
     */
    int lengthOfShortestPathBetween(String start, String dest);

    /**
     * Distance of route.
     *
     * @param townNames town names in the order as they should be visited.
     * @return length of the route.
     * @throws LLTownMapImpl.NoSuchRouteException if no such route exists.
     */
    int distance(String... townNames);

    /**
     * Count number of possible routes starting from start and ending at dest with a maximum number of {@code maxHops} .
     *
     * @param start   name of starting town of route.
     * @param dest    name of destination town of route.
     * @param maxHops maximum number of stops in route.
     * @return number of possible routes.
     */
    int countRoutesWithMaxHops(String start, String dest, int maxHops);

    /**
     * Number of possible routes starting from start and ending at dest with a exactly {@code hops}  number of stops.
     *
     * @param start name of starting town of route.
     * @param dest  name of destination town of route.
     * @param hops  number of stops in route.
     * @return number of possible routes.
     */
    int countRoutesWithHops(String start, String dest, int hops);

    /**
     * Number of possible routes starting from start and ending at dest with a maximum distance of {@code maxDistance}.
     *
     * @param start       name of starting town of route.
     * @param dest        name of destination town of route.
     * @param maxDistance maximum allowed distance.
     * @return number of possible routes.
     */
    int countRoutesWithMaxDistance(String start, String dest, int maxDistance);
}
