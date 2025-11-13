package com.qpkq.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserLinkTest {

    private UserLink linkUnlimited;
    private UserLink linkLimited;

    @BeforeEach
    void setUp() {
        linkUnlimited = new UserLink(
                "https://example.com",
                "http://short.ly/abc123",
                LocalDateTime.now().plusHours(1),
                0,
                0
        );

        linkLimited = new UserLink(
                "https://example.org",
                "http://short.ly/xyz789",
                LocalDateTime.now().plusHours(1),
                0,
                2
        );
    }

    @Test
    void testIsExpired() {
        assertFalse(linkUnlimited.isExpired());

        linkUnlimited.expiresAt = LocalDateTime.now().minusMinutes(1);
        assertTrue(linkUnlimited.isExpired());
    }

    @Test
    void testIsLimitReached() {
        assertFalse(linkLimited.isLimitReached());

        linkLimited.incrementClick();
        assertFalse(linkLimited.isLimitReached());

        linkLimited.incrementClick();
        assertTrue(linkLimited.isLimitReached());
    }

    @Test
    void testIncrementClick() {
        assertEquals(0, linkLimited.clicksCount);
        linkLimited.incrementClick();
        assertEquals(1, linkLimited.clicksCount);
    }

    @Test
    void testIsActive() {
        assertTrue(linkUnlimited.isActive());

        assertTrue(linkLimited.isActive());

        linkLimited.incrementClick();
        linkLimited.incrementClick();
        assertFalse(linkLimited.isActive());

        linkUnlimited.expiresAt = LocalDateTime.now().minusMinutes(1);
        assertFalse(linkUnlimited.isActive());
    }
}
