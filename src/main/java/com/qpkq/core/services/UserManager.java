package com.qpkq.core.services;

import com.qpkq.core.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

    /**
     * Users Map.
     */
    private static final Map<UUID, User> users = new HashMap<>();

    /**
     * Get User by UUID. If not exists - create.
     */
    public static User getOrCreate(UUID uuid) {
        return users.computeIfAbsent(uuid, User::new);
    }
}
