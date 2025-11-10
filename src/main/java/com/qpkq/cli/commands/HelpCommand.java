package com.qpkq.cli.commands;

import com.qpkq.cli.CliContext;
import com.qpkq.cli.CommandRegistry;
import com.qpkq.interfaces.cli.commands.CliCommand;

public class HelpCommand implements CliCommand {

    /**
     * CommandRegistry instance.
     */
    private final CommandRegistry registry;

    /**
     * HelpCommand constructor.
     *
     * @param registry CommandRegistry instance.
     */
    public HelpCommand(CommandRegistry registry) {
        this.registry = registry;
    }

    /**
     * Get Command Name.
     *
     * @return name.
     */
    @Override
    public String getName() {
        return "help";
    }

    /**
     * Get Command Description.
     *
     * @return description.
     */
    @Override
    public String getDescription() {
        return "Help command. List of available commands.";
    }

    /**
     * Handle Command.
     *
     * @param context CliContext instance.
     * @param args command args.
     */
    @Override
    public void execute(CliContext context, String[] args) {
        System.out.println("Available commands:");

        this.registry.all().forEach((_, command)
                -> System.out.printf("  %-10s - %s%n", command.getName(), command.getDescription()));
    }
}
