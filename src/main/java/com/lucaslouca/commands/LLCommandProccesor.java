package com.lucaslouca.commands;

import com.lucaslouca.util.LLPropertyFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class that processes {@code LLCommand} commands.
 */
public class LLCommandProccesor {
    private List<LLCommand> commands;
    private LLCommandFactory commandFactory;

    /**
     * Create a new {@code LLCommandProccesor} that load the command instructions from {@code path} and
     * uses the provided {@code commandFactory} to create them.
     *
     * @param path           absolute path to file containing the command instructions.
     * @param commandFactory {@code LLCommandFactory} that will create the commands from the input file.
     */
    public LLCommandProccesor(String path, LLCommandFactory commandFactory) {
        this.commandFactory = commandFactory;

        try {
            init(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Init commands from file at given path.
     *
     * @param path file containing the command instructions.
     * @throws FileNotFoundException if file is not found.
     */
    private void init(String path) throws FileNotFoundException {
        commands = new ArrayList<LLCommand>();

        Scanner in = new Scanner(new FileInputStream(path));

        while (in.hasNext()) {
            String input = in.next();
            commands.add(commandFactory.createCommand(input));
        }
    }

    /***************************************************************************************************/
    /*                                      PUBLIC METHODS                                             *
    /***************************************************************************************************/

    /**
     * Execute all commands currently in {@code LLCommandProccesor}.
     *
     * @return combined results of command.
     * @throws NoSuchElementException if no commands are available.
     */
    public String runAll() {
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
