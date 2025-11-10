package com.qpkq.interfaces.cli.commands;

import com.qpkq.cli.CliContext;

public interface CliCommand {

    /**
     * Get Command Name.
     *
     * @return name.
     */
    String getName();

    /**
     * Get Command Description.
     *
     * @return description.
     */
    String getDescription();

    /**
     * Execute Command.
     *
     * @param context CliContext instance.
     * @param args command args.
     */
    void execute(CliContext context, String[] args);
}
