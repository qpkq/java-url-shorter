package com.qpkq.cli;

import com.qpkq.interfaces.cli.commands.CliCommand;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandRegistry {

    /**
     * Commands Map.
     */
    private final Map<String, CliCommand> commands = new LinkedHashMap<>();

    /**
     * Register command.
     *
     * @param name command name.
     * @param command CliCommand instance.
     */
    public void register(String name, CliCommand command) {
        this.commands.put(name.toLowerCase(), command);
    }

    /**
     * Get Command.
     *
     * @param name command name.
     * @return CliCommand instance.
     */
    public CliCommand get(String name) {
        return this.commands.get(name.toLowerCase());
    }

    /**
     * Get All Commands.
     *
     * @return Commands Map.
     */
    public Map<String, CliCommand> all() {
        return this.commands;
    }
}
