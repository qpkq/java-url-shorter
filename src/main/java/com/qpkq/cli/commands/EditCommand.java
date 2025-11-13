package com.qpkq.cli.commands;

import com.qpkq.cli.CliContext;
import com.qpkq.core.models.UserLink;
import com.qpkq.interfaces.cli.commands.CliCommand;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EditCommand implements CliCommand {

    /**
     * Get Command Name.
     *
     * @return name.
     */
    @Override
    public String getName() {
        return "edit";
    }

    /**
     * Get Command Description.
     *
     * @return description.
     */
    @Override
    public String getDescription() {
        return "Edit your short URL.";
    }

    /**
     * Execute Command.
     *
     * @param context CliContext instance.
     * @param args command args.
     */
    @Override
    public void execute(CliContext context, String[] args) {
        Scanner scanner = new Scanner(System.in);

        String shortLink = args.length > 0 ? args[0] : null;
        if (shortLink == null) {
            System.out.print("Enter short URL to edit: ");
            shortLink = scanner.nextLine().trim();
        }

        UserLink link = context.currentUser().links.get(shortLink);
        if (link == null) {
            System.out.println("❌ No such short URL found.");
            return;
        }

        System.out.println("Edit URL: " + shortLink);
        System.out.println("Leave field empty to keep current value.");

        this.updateOriginalUrl(scanner, link);
        this.updateClickLimit(scanner, link);
        this.updateExpiration(scanner, link);

        System.out.println("\n✅ Short URL updated successfully!");
        System.out.println("Short link: " + link.shortLink);
        System.out.println("Original: " + link.originalLink);
        System.out.println("Expires at: " + link.expiresAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        System.out.println(link.maxClicks > 0
                ? "Click limit: " + link.maxClicks
                : "Unlimited link");
    }

    /**
     * Update Original URL.
     *
     * @param scanner Scanner instance.
     * @param link UserLink instance.
     */
    private void updateOriginalUrl(Scanner scanner, UserLink link) {
        System.out.print("New original URL (current: " + link.originalLink + "): ");
        String newUrl = scanner.nextLine().trim();

        if (! newUrl.isEmpty()) {
            try {
                URI uri = new URI(newUrl);

                boolean validScheme = "http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme());
                boolean hasHost = uri.getHost() != null;

                if (validScheme && hasHost) {
                    link.originalLink = newUrl;
                } else {
                    System.out.println("⚠️ Invalid URL format. Must start with http:// or https:// and have a valid domain. Keeping old value.");
                }
            } catch (URISyntaxException e) {
                System.out.println("⚠️ Invalid URL format. Keeping old value.");
            }
        }
    }

    /**
     * Update Click Limit.
     *
     * @param scanner Scanner instance.
     * @param link UserLink instance.
     */
    private void updateClickLimit(Scanner scanner, UserLink link) {
        System.out.print("New click limit (current: " + (link.maxClicks == 0 ? "unlimited" : link.maxClicks) + "): ");
        String limitInput = scanner.nextLine().trim();

        if (! limitInput.isEmpty()) {
            try {
                int newLimit = Integer.parseInt(limitInput);
                if (newLimit < 0) {
                    System.out.println("⚠️ Limit cannot be negative. Keeping old value.");
                } else {
                    link.maxClicks = newLimit;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid number. Keeping old limit.");
            }
        }
    }

    /**
     * Update Expiration.
     *
     * @param scanner Scanner instance.
     * @param link UserLink instance.
     */
    private void updateExpiration(Scanner scanner, UserLink link) {
        System.out.print("New expiration date (dd.MM.yyyy HH:mm): ");
        String expInput = scanner.nextLine().trim();

        if (! expInput.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                link.expiresAt = LocalDateTime.parse(expInput, formatter);
            } catch (Exception e) {
                System.out.println("⚠️ Invalid date format. Keeping old expiration.");
            }
        }
    }
}
