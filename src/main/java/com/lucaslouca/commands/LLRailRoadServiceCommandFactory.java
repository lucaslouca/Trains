package com.lucaslouca.commands;

import com.lucaslouca.service.LLRailRoadService;
import com.lucaslouca.util.LLPropertyFactory;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * This class implements the {@code LLCommandFactory} {@code Interface}. It provides methods for creating concrete command implementations of
 * the {@code abstract} type {@code LLAbstractRailRoadServiceCommand}. {@code LLAbstractRailRoadServiceCommand} are commands
 * that implement the {@code LLCommand} {@code Interface} but are specifically implemented for the {@code LLRailRoadService}.
 */
public class LLRailRoadServiceCommandFactory implements LLCommandFactory {
    private final String CMD_DISTANCE = "distance";
    private final String CMD_SHORTEST_PATH = "shortest_path";
    private final String CMD_LENGTH_SHORTEST_PATH = "length_of_shortest_path";
    private final String CMD_ROUTES_WITH_MAX_HOPS = "count_routes_with_max_hops";
    private final String CMD_ROUTES_WITH_HOPS = "count_routes_with_hops";
    private final String CMD_ROUTES_WITH_MAX_DISTANCE = "count_routes_with_max_distance";

    private final LLRailRoadService service;

    /**
     * Create a new {@code LLRailRoadServiceCommandFactory} that sets the given {@code LLRailRoadService} as a receiver for the
     * commands.
     *
     * @param service {@code LLRailRoadService} that will be set as the receiver in the commands that this factory will create.
     */
    public LLRailRoadServiceCommandFactory(LLRailRoadService service) {
        this.service = service;
    }

    /**
     * Create and return a new {@code LLAbstractRailRoadServiceCommand} based on the input.
     * <p>
     * Input has the following form:
     * <p>
     * {@code commandName;town1;town2;...townN} for distance calculation
     * <p>
     * or
     * <p>
     * {@code commandName;town1;town2} for shortest path
     * <p>
     * or
     * <p>
     * {@code commandName;town1;town2;N} for {@code countRoutesWithXXX()} functions
     *
     * @param input the input to parse.
     * @return an {code LLAbstractRailRoadServiceCommand} that implements the {@code LLCommand} interface.
     * @throws NoSuchElementException if no such command available.
     * @throws IllegalArgumentException if input format is invalid.
     */
    public LLAbstractRailRoadServiceCommand createCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length <= 1) {
            throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.service_command_factory.illegal_argument.parse_input_format", input));
        } else {
            String name = parts[0];

            switch (name) {
                case CMD_SHORTEST_PATH:
                    return createShortestPathCommand(input);
                case CMD_LENGTH_SHORTEST_PATH:
                    return createShortestPathLengthCommand(input);
                case CMD_DISTANCE:
                    return createDistanceCommand(input);
                case CMD_ROUTES_WITH_MAX_HOPS:
                    return createCountRoutesWithMaxHopsCommand(input);
                case CMD_ROUTES_WITH_HOPS:
                    return createCountRoutesWithHopsCommand(input);
                case CMD_ROUTES_WITH_MAX_DISTANCE:
                    return createCountRoutesWithMaxDistanceCommand(input);
                default:
                    throw new NoSuchElementException(LLPropertyFactory.propertyWithArgs("exception.service_command_factory.illegal_argument.parse_input_unknown", name));
            }

        }
    }


    /**
     * Create a new {@code LLShortestPathCommand}.
     *
     * @param input {@code String} (including command name) that needs to be parsed and then passed as parameters to the new {@code LLShortestPathCommand}.
     * @return command {@code LLShortestPathCommand} for the given parameters.
     * @throws IllegalArgumentException if format is invalid.
     */
    private LLShortestPathCommand createShortestPathCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length != 3) {
            throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.service_command_factory.illegal_argument.parse_input_format", input));
        } else {
            LLShortestPathCommand command = new LLShortestPathCommand(service);

            command.setStart(parts[1]);
            command.setDest(parts[2]);

            return command;
        }
    }

    /**
     * Create a new {@code LLShortestPathLengthCommand}.
     *
     * @param input {@code String} (including command name) that needs to be parsed and then passed as parameters to the new {@code LLShortestPathLengthCommand}.
     * @return command {@code LLShortestPathLengthCommand} for the given parameters.
     * @throws IllegalArgumentException if format is invalid.
     */
    private LLShortestPathLengthCommand createShortestPathLengthCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length != 3) {
            throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.service_command_factory.illegal_argument.parse_input_format", input));
        } else {
            LLShortestPathLengthCommand command = new LLShortestPathLengthCommand(service);

            command.setStart(parts[1]);
            command.setDest(parts[2]);

            return command;
        }
    }

    /**
     * Create a new {@code LLDistanceCommand}.
     *
     * @param input {@code String} (including command name) that needs to be parsed and then passed as parameters to the new {@code LLDistanceCommand}.
     * @return command {@code LLDistanceCommand} for the given parameters.
     * @throws IllegalArgumentException if format is invalid.
     */
    private LLDistanceCommand createDistanceCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length < 2) {
            throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.service_command_factory.illegal_argument.parse_input_format", input));
        } else {
            LLDistanceCommand command = new LLDistanceCommand(service);

            String[] townNames = Arrays.copyOfRange(parts, 1, parts.length);
            command.setTownNames(townNames);

            return command;
        }
    }

    /**
     * Create a new {@code LLCountRoutesWithMaxHopsCommand}.
     *
     * @param input {@code String} (including command name) that needs to be parsed and then passed as parameters to the new {@code LLCountRoutesWithMaxHopsCommand}.
     * @return command {@code LLCountRoutesWithMaxHopsCommand} for the given parameters.
     * @throws IllegalArgumentException if format is invalid.
     */
    private LLCountRoutesWithMaxHopsCommand createCountRoutesWithMaxHopsCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length != 4) {
            throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.service_command_factory.illegal_argument.parse_input_format", input));
        } else {
            LLCountRoutesWithMaxHopsCommand command = new LLCountRoutesWithMaxHopsCommand(service);

            command.setStart(parts[1]);
            command.setDest(parts[2]);

            try {
                command.setMaxHops(Integer.parseInt(parts[3]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.service_command_factory.illegal_argument.parse_input_format", input));
            }

            return command;
        }
    }

    /**
     * Create a new {@code LLCountRoutesWithHopsCommand}.
     *
     * @param input {@code String} (including command name) that needs to be parsed and then passed as parameters to the new {@code LLCountRoutesWithHopsCommand}.
     * @return command {@code LLCountRoutesWithHopsCommand} for the given parameters.
     * @throws IllegalArgumentException if format is invalid.
     */
    private LLCountRoutesWithHopsCommand createCountRoutesWithHopsCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length != 4) {
            throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.service_command_factory.illegal_argument.parse_input_format", input));
        } else {
            LLCountRoutesWithHopsCommand command = new LLCountRoutesWithHopsCommand(service);

            command.setStart(parts[1]);
            command.setDest(parts[2]);

            try {
                command.setHops(Integer.parseInt(parts[3]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.service_command_factory.illegal_argument.parse_input_format", input));
            }

            return command;
        }
    }

    /**
     * Create a new {@code LLCountRoutesWithMaxDistanceCommand}.
     *
     * @param input {@code String} (including command name) that needs to be parsed and then passed as parameters to the new {@code LLCountRoutesWithMaxDistanceCommand}.
     * @return command {@code LLCountRoutesWithMaxDistanceCommand} for the given parameters.
     * @throws IllegalArgumentException if format is invalid.
     */
    private LLCountRoutesWithMaxDistanceCommand createCountRoutesWithMaxDistanceCommand(String input) {
        String[] parts = input.split(";");

        if (parts.length != 4) {
            throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.service_command_factory.illegal_argument.parse_input_format", input));
        } else {
            LLCountRoutesWithMaxDistanceCommand command = new LLCountRoutesWithMaxDistanceCommand(service);

            command.setStart(parts[1]);
            command.setDest(parts[2]);

            try {
                command.setMaxDistance(Integer.parseInt(parts[3]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(LLPropertyFactory.propertyWithArgs("exception.service_command_factory.illegal_argument.parse_input_format", input));
            }

            return command;
        }
    }
}
