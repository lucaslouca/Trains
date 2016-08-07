package com.lucaslouca.service;

import com.lucaslouca.util.LLPropertyFactory;
import com.lucaslouca.util.LLTownMap;
import com.lucaslouca.util.LLTownMapImpl;

/**
 * {@code LLRailRoadServiceImpl} implements the {@code LLRailRoadService} {@code Interface}.
 * <p>
 * It makes use of {@code LLTownMap}. Although most functionality in {@code LLRailRoadServiceImpl} is cascaded to
 * {@code LLTownMap}, the idea of providing {@code LLRailRoadServiceImpl} is to separate the functionality between
 * a service system and a map. That is, {@code LLRailRoadServiceImpl} could be expanded to support further functionality such as
 * {@code requestClosingHours()} or {@code nextTrainDepartureTime()} without the need to modify the {@code LLTownMap}.
 */
public class LLRailRoadServiceImpl implements LLRailRoadService {
    private LLTownMap map;

    /**
     * Create a new {@code LLRailRoadServiceImpl} that has the given map.
     *
     * @param map {@code LLTownMap} for the {@code LLRailRoadServiceImpl} to use.
     */
    public LLRailRoadServiceImpl(LLTownMap map) {
        this.map = map;
    }

    /***************************************************************************************************/
    /*                                      PUBLIC METHODS                                             *
    /***************************************************************************************************/

    /**
     * {@code String} representing the shortest path between start and dest.
     *
     * @param start start town.
     * @param dest  town which we want to reach.
     * @return {@code String} representing the shortest path between start and dest.
     * @throws LLTownMapImpl.NoSuchRouteException if no route exists between the two towns.
     */
    @Override
    public String shortestPathBetween(String start, String dest) {
        try {
            return map.shortestPathBetween(start, dest);
        } catch (LLTownMapImpl.NoSuchRouteException e) {
            throw new LLRailRoadServiceException(LLPropertyFactory.getProperties().get("exception.rail_road_service.no_route"));
        }
    }

    /**
     * Distance of shortest path between start and dest.
     *
     * @param start name of start town.
     * @param dest  name of town which we want to reach.
     * @return length of the shortest path between start and dest.
     * @throws LLTownMapImpl.NoSuchRouteException if no route exists between the two towns.
     */
    @Override
    public int lengthOfShortestPathBetween(String start, String dest) {
        try {
            return map.lengthOfShortestPathBetween(start, dest);
        } catch (LLTownMapImpl.NoSuchRouteException e) {
            throw new LLRailRoadServiceException(LLPropertyFactory.getProperties().get("exception.rail_road_service.no_route"));
        }
    }

    /**
     * Distance of route.
     *
     * @param townNames town names in the order as they should be visited.
     * @return length of the route.
     * @throws LLTownMapImpl.NoSuchRouteException if no such route exists.
     */
    @Override
    public int distance(String... townNames) {
        try {
            return map.distance(townNames);
        } catch (LLTownMapImpl.NoSuchRouteException e) {
            throw new LLRailRoadServiceException(LLPropertyFactory.getProperties().get("exception.rail_road_service.no_route"));
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
        return map.countRoutesWithMaxHops(start, dest, maxHops);
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
        return map.countRoutesWithHops(start, dest, hops);
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
        return map.countRoutesWithMaxDistance(start, dest, maxDistance);
    }
}
