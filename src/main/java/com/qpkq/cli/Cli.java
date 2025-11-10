package com.qpkq.cli;

import com.qpkq.cli.commands.*;
import com.qpkq.core.services.UserManager;
import com.qpkq.interfaces.cli.commands.CliCommand;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class Cli {

    /**
     * CommandRegistry instance.
     */
    private final CommandRegistry registry;

    /**
     * Scanner Instance.
     */
    private final Scanner scanner;

    /**
     * CliContext instance.
     */
    private final CliContext context;

    /**
     * Cli constructor.
     */
    public Cli() {
        this.scanner    = new Scanner(System.in);
        this.context    = new CliContext(UserManager.getOrCreate(UUID.randomUUID()));
        this.registry   = new CommandRegistry();

        this.registry.register("su", new SwitchUserCommand());
        this.registry.register("create", new CreateCommand());
        this.registry.register("open", new OpenCommand());
        this.registry.register("list", new ListCommand());
        this.registry.register("delete", new DeleteCommand());
        this.registry.register("help", new HelpCommand(this.registry));
        this.registry.register("exit", new ExitCommand());
    }

    /**
     * Starts the CLI interface.
     */
    public void start() {
        System.out.println("================== URL Shortener ==================");
        System.out.println("User ID: " + this.context.currentUser().id);
        this.registry.get("help").execute(this.context, new String[0]);

        while (true) {
            System.out.print("> ");
            String input = this.scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split(" ");
            String commandName = parts[0].toLowerCase();

            CliCommand command = this.registry.get(commandName);
            if (command != null) {
                String[] args = Arrays.copyOfRange(parts, 1, parts.length);
                command.execute(this.context, args);
            } else {
                System.out.println("Unknown command: " + commandName);
                System.out.println("Type 'help' for available commands");
            }
        }
    }
}
