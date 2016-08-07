package com.lucaslouca.commands;

import com.lucaslouca.service.LLRailRoadService;

/**
 * {@code LLAbstractRailRoadServiceCommand} that computes the number of possible routes starting from
 * start and ending at dest.
 */
public abstract class LLAbstractStartDestinationCommand extends LLAbstractRailRoadServiceCommand {
    protected String start;
    protected String dest;

    /**
     * Create a new command.
     *
     * @param service receiver which will be called from this command.
     */
    public LLAbstractStartDestinationCommand(LLRailRoadService service) {
        super(service);
    }

    /**
     * Set start town.
     *
     * @param start start town.
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Set destination town.
     *
     * @param dest destination town.
     */
    public void setDest(String dest) {
        this.dest = dest;
    }
}
