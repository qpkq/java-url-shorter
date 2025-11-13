package com.qpkq.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        user = new User(id);
    }

    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertEquals(id, user.id);
        assertNotNull(user.createdAt);
        assertNotNull(user.links);
        assertTrue(user.links.isEmpty());
    }

    @Test
    void testAddLink() {
        UserLink link = new UserLink(
                "https://example.com",
                "http://short.ly/abc123",
                java.time.LocalDateTime.now().plusHours(1),
                0,
                0
        );

        user.links.put(link.shortLink, link);

        assertEquals(1, user.links.size());
        assertTrue(user.links.containsKey(link.shortLink));
        assertEquals(link, user.links.get(link.shortLink));
    }

    @Test
    void testRemoveLink() {
        UserLink link = new UserLink(
                "https://example.com",
                "http://short.ly/abc123",
                java.time.LocalDateTime.now().plusHours(1),
                0,
                0
        );

        user.links.put(link.shortLink, link);
        assertEquals(1, user.links.size());

        user.links.remove(link.shortLink);
        assertTrue(user.links.isEmpty());
    }
}
