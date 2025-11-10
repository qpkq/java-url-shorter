package com.qpkq.cli.commands;

import com.qpkq.cli.CliContext;
import com.qpkq.core.models.UserLink;
import com.qpkq.core.services.LinkService;
import com.qpkq.interfaces.cli.commands.CliCommand;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CreateCommand implements CliCommand {

    /**
     * LinkService instance.
     */
    private final LinkService linkService = new LinkService();

    /**
     * Get Command Name.
     *
     * @return name.
     */
    @Override
    public String getName() {
        return "create";
    }

    /**
     * Get Command Description.
     *
     * @return description.
     */
    @Override
    public String getDescription() {
        return "Create Short URL command.";
    }

    /**
     * Execute create command.
     */
    @Override
    public void execute(CliContext context, String[] args) {
        Scanner scanner = new Scanner(System.in);

        String originalUrl = args.length > 0 ? args[0] : null;

        if (originalUrl == null) {
            System.out.print("Enter original link: ");
            originalUrl = scanner.nextLine().trim();
        }

        System.out.print("Enter click limit (0 for unlimited): ");
        int limit = 0;
        try {
            String input = scanner.nextLine().trim();
            limit = input.isEmpty() ? 0 : Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, using unlimited");
        }

        try {
            UserLink link = linkService.createLink(context.currentUser(), originalUrl, limit);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

            System.out.println("\nCreated successfully!");
            System.out.println("Short link: " + link.shortLink);
            System.out.println("Original: " + link.originalLink);
            System.out.println("Expires at: " + link.expiresAt.format(formatter));
            System.out.println(limit > 0
                    ? "Click limit: " + limit
                    : "Unlimited link");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
