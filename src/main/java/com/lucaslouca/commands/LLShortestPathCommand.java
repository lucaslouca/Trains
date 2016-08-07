package com.lucaslouca.commands;

import com.lucaslouca.service.LLRailRoadService;

/**
 * {code LLAbstractStartDestinationCommand} that computes the shortest path between two towns.
 */
public class LLShortestPathCommand extends LLAbstractStartDestinationCommand {
    /**
     * Create a new command.
     *
     * @param service receiver which will be called from this command.
     */
    public LLShortestPathCommand(LLRailRoadService service) {
        super(service);
    }

    /**
     * Compute shortest path between two towns.
     *
     * @return shortest path between two towns.
     */
    @Override
    public String execute() {
        return getReceiver().shortestPathBetween(start, dest);
    }
}
