package com.qpkq.core.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {

    /**
     * UUID.
     */
    public UUID id;

    /**
     * Created At timestamp.
     */
    public LocalDateTime createdAt;

    /**
     * Links.
     */
    public Map<String, UserLink> links = new HashMap<>();

    /**
     * User constructor.
     *
     * @param id UUID.
     */
    public User(UUID id) {
        this.id         = id;
        this.createdAt  = LocalDateTime.now();
    }
}
