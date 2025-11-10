package com.qpkq.cli.commands;

import com.qpkq.cli.CliContext;
import com.qpkq.core.models.UserLink;
import com.qpkq.core.services.LinkService;
import com.qpkq.interfaces.cli.commands.CliCommand;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ListCommand implements CliCommand {

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
        return "list";
    }

    /**
     * Get Command Description.
     *
     * @return description.
     */
    @Override
    public String getDescription() {
        return "List your short URLs.";
    }

    /**
     * Execute Command.
     *
     * @param context CliContext instance.
     * @param args command args.
     */
    @Override
    public void execute(CliContext context, String[] args) {
        linkService.cleanExpiredLinks(context.currentUser());

        Map<String, UserLink> links = context.currentUser().links;

        if (links.isEmpty()) {
            System.out.println("You have no short URLs yet.");
            return;
        }

        System.out.println("Your short URLs:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        links.values().forEach(link -> {
            String status;
            if (!link.isActive()) {
                if (link.isExpired()) {
                    status = "❌ Expired";
                } else if (link.isLimitReached()) {
                    status = "⚠️ Limit reached";
                } else {
                    status = "Inactive";
                }
            } else {
                status = "✅ Active";
            }

            System.out.printf(
                    "- %s -> %s | Clicks: %d/%s | Expires: %s | Status: %s%n",
                    link.shortLink,
                    link.originalLink,
                    link.clicksCount,
                    link.maxClicks == 0 ? "∞" : link.maxClicks,
                    link.expiresAt.format(formatter),
                    status
            );
        });
    }
}
