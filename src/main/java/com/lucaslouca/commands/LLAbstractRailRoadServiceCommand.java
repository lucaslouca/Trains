package com.lucaslouca.commands;

import com.lucaslouca.service.LLRailRoadService;

/**
 * {@code Abstract} class defining an {@code LLCommand} related to the {@code LLRailRoadService} functionality.
 */
public abstract class LLAbstractRailRoadServiceCommand implements LLCommand  {
    private LLRailRoadService receiver;

    /**
     * Create a new command.
     *
     * @param service receiver which will be called from this command.
     */
    public LLAbstractRailRoadServiceCommand(LLRailRoadService service){
        this.receiver = service;
    }

    /**
     * The {@code LLRailRoadService} that will be called from this command.
     *
     * @return {@code LLRailRoadService} that will be called from this command.
     */
    protected LLRailRoadService getReceiver() {
        return this.receiver;
    }
}
