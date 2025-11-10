package com.qpkq.core.services;

import com.qpkq.core.models.User;
import com.qpkq.core.models.UserLink;
import com.qpkq.infrastructure.config.Configurator;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

public class LinkService {

    /**
     * Create new short link for user.
     *
     * @param user target user
     * @param originalLink original URL
     * @param maxClicks maximum clicks allowed (0 = unlimited)
     * @return created UserLink
     */
    public UserLink createLink(User user, String originalLink, int maxClicks) {
        if (originalLink == null || originalLink.isEmpty()) {
            throw new IllegalArgumentException("Original link cannot be empty");
        }

        this.validateUrl(originalLink);

        String shortLink = Configurator.get("BASE_URL") + "/" + UUID.randomUUID().toString().substring(0, 7);

        LocalDateTime expiresAt = LocalDateTime.now().plusHours(
                Long.parseLong(Configurator.get("LINK_TTL_HOURS"))
        );

        UserLink link = new UserLink(originalLink, shortLink, expiresAt, 0, maxClicks);

        user.links.put(shortLink, link);

        return link;
    }

    /**
     * Delete Expires URLs.
     *
     * @param user target user
     */
    public void cleanExpiredLinks(User user) {
        user.links.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    /**
     * Validate URL format.
     *
     * @param url URL string
     */
    private void validateUrl(String url) {
        try {
            URI uri = new URI(url);

            if (uri.getScheme() == null || uri.getHost() == null) {
                throw new IllegalArgumentException("Invalid URL: " + url);
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL: " + url);
        }
    }
}
