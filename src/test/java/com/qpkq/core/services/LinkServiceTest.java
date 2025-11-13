package com.qpkq.core.services;

import com.qpkq.core.models.User;
import com.qpkq.core.models.UserLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LinkServiceTest {

    private LinkService linkService;
    private User user;

    @BeforeEach
    void setup() {
        linkService = new LinkService();
        user = new User(UUID.randomUUID());
        System.setProperty("BASE_URL", "http://localhost:8080");
        System.setProperty("LINK_TTL_HOURS", "1");
    }

    @Test
    void testCreateLinkSuccess() {
        UserLink link = linkService.createLink(user, "https://example.com", 5);

        assertNotNull(link, "Link should be created");
        assertEquals("https://example.com", link.originalLink);
        assertTrue(link.shortLink.startsWith("http://localhost:8080/"));
        assertEquals(0, link.clicksCount);
        assertEquals(5, link.maxClicks);
        assertFalse(link.isExpired(), "New link should not be expired");
        assertTrue(user.links.containsKey(link.shortLink), "User should have the link");
    }

    @Test
    void testCreateLinkInvalidUrl() {
        assertThrows(IllegalArgumentException.class, () ->
                linkService.createLink(user, "invalid-url", 0)
        );
    }

    @Test
    void testCleanExpiredLinks() {
        LocalDateTime past = LocalDateTime.now().minusHours(1);
        UserLink expired = new UserLink("https://expired.com", "short1", past, 0, 0);
        user.links.put(expired.shortLink, expired);

        linkService.cleanExpiredLinks(user);

        assertFalse(user.links.containsKey(expired.shortLink), "Expired link should be removed");
    }

    @Test
    void testUnlimitedClicks() {
        UserLink link = linkService.createLink(user, "https://example.com", 0);
        assertEquals(0, link.maxClicks, "0 maxClicks means unlimited");
    }
}
