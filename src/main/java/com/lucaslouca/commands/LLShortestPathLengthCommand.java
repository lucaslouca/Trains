package com.lucaslouca.commands;

import com.lucaslouca.service.LLRailRoadService;


/**
 * {code LLAbstractStartDestinationCommand} that computes the length of the shortest path between two towns.
 */
public class LLShortestPathLengthCommand extends LLAbstractStartDestinationCommand {
    /**
     * Create a new command.
     *
     * @param service receiver which will be called from this command.
     */
    public LLShortestPathLengthCommand(LLRailRoadService service) {
        super(service);
    }

    /**
     * Compute the length of the shortest path between two towns.
     *
     * @return length of the shortest path between two towns.
     */
    @Override
    public Integer execute() {
        return getReceiver().lengthOfShortestPathBetween(start, dest);
    }
}
