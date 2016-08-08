package com.lucaslouca.commands;

import com.lucaslouca.util.LLPropertyFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class that executes {@code LLCommand} commands.
 */
public class LLCommandProccesor {
    private LLCommandFactory commandFactory;

    /**
     * Create a new {@code LLCommandProccesor} that uses the provided {@code commandFactory} to create commands.
     *
     * @param commandFactory {@code LLCommandFactory} that will create the commands.
     */
    public LLCommandProccesor(LLCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }


    /**
     * Parse commands from file at given path.
     *
     * @param path file containing the command instructions.
     * @return {@code List<LLCommand>} containing the parsed commands.
     * @throws FileNotFoundException if file is not found.
     */
    private List<LLCommand> loadFile(String path) throws FileNotFoundException {
        List<LLCommand> commands = new ArrayList<LLCommand>();

        Scanner in = new Scanner(new FileInputStream(path));

        while (in.hasNext()) {
            String input = in.next();
            commands.add(commandFactory.createCommand(input));
        }

        return commands;
    }

    /***************************************************************************************************/
    /*                                      PUBLIC METHODS                                             *
    /***************************************************************************************************/

    /**
     * Execute all commands from a file.
     *
     * @param path file containing the command instructions.
     * @return combined results of commands.
     * @throws NoSuchElementException if no commands are available.
     * @throws FileNotFoundException if file is not found.
     */
    public String runAll(String path) throws FileNotFoundException {
        List<LLCommand> commands = loadFile(path);

        if (!commands.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < commands.size(); i++) {
                LLCommand command = commands.get(i);
                sb.append(command.execute());
                sb.append("\n");
            }

            LLCommand command = commands.get(commands.size() - 1);
            sb.append(command.execute());

            return sb.toString();
        } else {
            throw new NoSuchElementException(LLPropertyFactory.getProperties().get("exception.command_processor.no_such_element"));
        }
    }

    /**
     * Run a single command.
     *
     * @param input command to run.
     * @return the result of the command execution.
     * @throws IllegalArgumentException if command syntax is not valid.
     */
    public String run(String input) {
        if (input != null) {
            try {
                LLCommand command = commandFactory.createCommand(input);
                Object result = command.execute();
                return result.toString();
            } catch (NoSuchElementException e) {
                throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.command_processor.illegal_argument.parse_input_format", input));
            }
        } else {
            throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.command_processor.illegal_argument.parse_input_format", input));
        }
    }
}
