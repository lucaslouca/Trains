package com.lucaslouca.commands;

/**
 * {@code Interface} that defines which functionality an {@code LLCommand} factory must provide.
 * The {@code LLCommandFactory} provides functionality for creating new commands that implement the {@code LLCommand} {@code interface}.
 */
public interface LLCommandFactory {
    /**
     * Create a new {@code LLCommand}.
     *
     * @param input {@code String} that will be parsed and passed as parameters to the concrete command implementation.
     * @return the newly created command.
     */
    LLCommand createCommand(String input);
}
