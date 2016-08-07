package com.lucaslouca.commands;

import com.lucaslouca.service.LLRailRoadService;


/**
 * {@code LLAbstractRailRoadServiceCommand} that computes the distance of a route.
 */
public class LLDistanceCommand extends LLAbstractRailRoadServiceCommand {
    private String[] townNames;

    /**
     * Create a new command.
     *
     * @param service receiver which will be called from this command.
     */
    public LLDistanceCommand(LLRailRoadService service) {
        super(service);
    }

    /**
     * Set the towns of the route
     *
     * @param townNames {@code String[]} containing the towns in the route.
     */
    public void setTownNames(String[] townNames) {
        this.townNames = townNames;
    }

    /**
     * Compute the distance of a route.
     *
     * @return distance of the route.
     */
    @Override
    public Integer execute() {
        return getReceiver().distance(townNames);
    }
}
