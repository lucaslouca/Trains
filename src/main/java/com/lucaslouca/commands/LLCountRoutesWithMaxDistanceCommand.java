package com.lucaslouca.commands;

import com.lucaslouca.service.LLRailRoadService;


/**
 * {@code LLAbstractStartDestinationCommand} that computes the number of possible routes starting from
 * start and ending at dest with a maximum distance.
 */
public class LLCountRoutesWithMaxDistanceCommand extends LLAbstractStartDestinationCommand {
    private int maxDistance;

    /**
     * Create a new command.
     *
     * @param service receiver which will be called from this command.
     */
    public LLCountRoutesWithMaxDistanceCommand(LLRailRoadService service) {
        super(service);
    }

    /**
     * Set max distance.
     *
     * @param maxDistance max distance.
     */
    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    /**
     * Number of possible routes starting from start and ending at dest with a maximum distance.
     *
     * @return number of possible routes.
     */
    @Override
    public Integer execute() {
        return getReceiver().countRoutesWithMaxDistance(start, dest, maxDistance);
    }
}
