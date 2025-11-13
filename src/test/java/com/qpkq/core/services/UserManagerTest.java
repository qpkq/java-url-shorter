package com.qpkq.core.services;

import com.qpkq.core.models.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    @Test
    void testCreateNewUser() {
        UUID uuid = UUID.randomUUID();
        User user = UserManager.getOrCreate(uuid);

        assertNotNull(user, "User should not be null");
        assertEquals(uuid, user.id, "User UUID should match the one provided");
    }

    @Test
    void testGetExistingUser() {
        UUID uuid = UUID.randomUUID();
        User first = UserManager.getOrCreate(uuid);
        User second = UserManager.getOrCreate(uuid);

        assertSame(first, second, "Should return the same User instance for the same UUID");
    }

    @Test
    void testDifferentUsersForDifferentUUIDs() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        User user1 = UserManager.getOrCreate(uuid1);
        User user2 = UserManager.getOrCreate(uuid2);

        assertNotSame(user1, user2, "Different UUIDs should produce different User instances");
    }
}
