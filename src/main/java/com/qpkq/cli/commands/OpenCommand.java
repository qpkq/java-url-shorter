package com.qpkq.cli.commands;

import com.qpkq.cli.CliContext;
import com.qpkq.core.models.UserLink;
import com.qpkq.core.services.LinkService;
import com.qpkq.interfaces.cli.commands.CliCommand;

import java.awt.*;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class OpenCommand implements CliCommand {

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
        return "open";
    }

    /**
     * Get Command Description.
     *
     * @return description.
     */
    @Override
    public String getDescription() {
        return "Open short URL.";
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

        linkService.cleanExpiredLinks(context.currentUser());

        String shortLink = args.length > 0 ? args[0] : null;

        if (shortLink == null) {
            System.out.print("Enter short URL to open: ");
            shortLink = scanner.nextLine().trim();
        }

        UserLink link = context.currentUser().links.get(shortLink);

        if (link == null) {
            System.out.println("❌ No such URL found.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        if (link.isExpired()) {
            System.out.println("❌ Link expired at: " + link.expiresAt.format(formatter));
            return;
        }

        if (link.isLimitReached()) {
            System.out.println("⚠️ Click limit reached: " + link.clicksCount + "/" + link.maxClicks);
            return;
        }

        try {
            link.incrementClick();
            System.out.println("✅ Opening: " + link.originalLink);

            Desktop.getDesktop().browse(new URI(link.originalLink));
        } catch (Exception e) {
            System.out.println("❌ Failed to open URL: " + e.getMessage());
        }
    }
}
