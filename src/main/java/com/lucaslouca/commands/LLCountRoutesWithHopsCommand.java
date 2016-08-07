package com.lucaslouca.commands;

import com.lucaslouca.service.LLRailRoadService;


/**
 * {@code LLAbstractStartDestinationCommand} that computes the number of possible routes starting from
 * start and ending at dest with an exact number of stops.
 */
public class LLCountRoutesWithHopsCommand extends LLAbstractStartDestinationCommand {
    private int hops;

    /**
     * Create a new command.
     *
     * @param service receiver which will be called from this command.
     */
    public LLCountRoutesWithHopsCommand(LLRailRoadService service) {
        super(service);
    }


    /**
     * Set hops number.
     *
     * @param hops number of hops.
     */
    public void setHops(int hops) {
        this.hops = hops;
    }

    /**
     * Number of possible routes starting from start and ending at dest with an exact number of stops.
     *
     * @return number of possible routes.
     */
    @Override
    public Integer execute() {
        return getReceiver().countRoutesWithHops(start, dest, hops);
    }
}
