package com.qpkq.cli.commands;

import com.qpkq.cli.CliContext;
import com.qpkq.interfaces.cli.commands.CliCommand;

public class ExitCommand implements CliCommand {

    /**
     * Get Command Name.
     *
     * @return name.
     */
    @Override
    public String getName() {
        return "exit";
    }

    /**
     * Get Command Description.
     *
     * @return description.
     */
    @Override
    public String getDescription() {
        return "Exit application";
    }

    /**
     * Handle Command.
     *
     * @param context CliContext instance.
     * @param args command args.
     */
    @Override
    public void execute(CliContext context, String[] args) {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}
