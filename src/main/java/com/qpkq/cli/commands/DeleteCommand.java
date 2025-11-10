package com.qpkq.cli.commands;

import com.qpkq.cli.CliContext;
import com.qpkq.interfaces.cli.commands.CliCommand;

import java.util.Scanner;

public class DeleteCommand implements CliCommand {

    /**
     * Get Command Name.
     *
     * @return name.
     */
    @Override
    public String getName() {
        return "delete";
    }

    /**
     * Get Command Description.
     *
     * @return description.
     */
    @Override
    public String getDescription() {
        return "Delete short URL.";
    }

    /**
     * Execute delete command.
     */
    @Override
    public void execute(CliContext context, String[] args) {
        Scanner scanner = new Scanner(System.in);

        String shortLink = args.length > 0 ? args[0] : null;

        if (shortLink == null) {
            System.out.print("Enter short URL to delete: ");
            shortLink = scanner.nextLine().trim();
        }

        if (context.currentUser().links.containsKey(shortLink)) {
            context.currentUser().links.remove(shortLink);
            System.out.println("✅ Short URL deleted: " + shortLink);
        } else {
            System.out.println("❌ No such URL found: " + shortLink);
        }
    }
}
