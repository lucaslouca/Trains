package com.lucaslouca.commands;

import com.lucaslouca.service.LLRailRoadService;


/**
 * {@code LLAbstractStartDestinationCommand} that computes the number of possible routes starting from
 * start and ending at dest with a maximum number of stops.
 */
public class LLCountRoutesWithMaxHopsCommand extends LLAbstractStartDestinationCommand {
    private int maxHops;

    /**
     * Create a new command.
     *
     * @param service receiver which will be called from this command.
     */
    public LLCountRoutesWithMaxHopsCommand(LLRailRoadService service) {
        super(service);
    }

    /**
     * Set maximum hops number.
     *
     * @param maxHops maximum number of hops.
     */
    public void setMaxHops(int maxHops) {
        this.maxHops = maxHops;
    }

    /**
     * Number of possible routes starting from start and ending at dest with a maximum number of stops.
     *
     * @return number of possible routes.
     */
    @Override
    public Integer execute() {
        return getReceiver().countRoutesWithMaxHops(start, dest, maxHops);
    }
}
