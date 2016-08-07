package com.lucaslouca.commands;

/**
 * {@code Interface} defining a command that can be executed.
 */
public interface LLCommand {
    /**
     * Run the command.
     *
     * @return the result of the execution event.
     */
    Object execute();
}
