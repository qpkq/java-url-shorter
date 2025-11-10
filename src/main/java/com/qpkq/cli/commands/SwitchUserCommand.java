package com.qpkq.cli.commands;

import com.qpkq.cli.CliContext;
import com.qpkq.core.models.User;
import com.qpkq.core.services.UserManager;
import com.qpkq.interfaces.cli.commands.CliCommand;

import java.util.UUID;

public class SwitchUserCommand implements CliCommand {

    /**
     * Get Command Name.
     *
     * @return name.
     */
    @Override
    public String getName() {
        return "su";
    }

    /**
     * Get Command Description.
     *
     * @return description.
     */
    @Override
    public String getDescription() {
        return "Switch user by UUID (create new if not exists). If no UUID is provided, creates new user.";
    }

    /**
     * Execute Command.
     *
     * @param context CliContext instance.
     * @param args command args.
     */
    @Override
    public void execute(CliContext context, String[] args) {
        User user;

        if (args.length == 0 || args[0].isEmpty()) {
            user = UserManager.getOrCreate(UUID.randomUUID());
            System.out.println("✅ Created new user with UUID: " + user.id);
        } else {
            UUID uuid;
            try {
                uuid = UUID.fromString(args[0]);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid UUID format.");
                return;
            }

            user = UserManager.getOrCreate(uuid);
            System.out.println("✅ Switched to user: " + user.id);
        }

        context.switchUser(user);
        System.out.println("You have " + user.links.size() + " short URLs.");
    }
}
