package com.qpkq.core.models;

import java.time.LocalDateTime;

public class UserLink {

    /**
     * Original Link.
     */
    public String originalLink;

    /**
     * Short Link.
     */
    public String shortLink;

    /**
     * Expires At timestamp.
     */
    public LocalDateTime expiresAt;

    /**
     * Click Counter.
     */
    public int clicksCount;

    /**
     * Maximum clicks allowed (0 = unlimited).
     */
    public int maxClicks;

    /**
     * UserLink constructor.
     *
     * @param originalLink original link
     * @param shortLink short link
     * @param expiresAt expires at timestamp
     * @param clicksCount initial clicks count
     * @param maxClicks maximum clicks allowed
     */
    public UserLink(String originalLink, String shortLink, LocalDateTime expiresAt, int clicksCount, int maxClicks) {
        this.originalLink   = originalLink;
        this.shortLink      = shortLink;
        this.expiresAt      = expiresAt;
        this.clicksCount    = clicksCount;
        this.maxClicks      = maxClicks;
    }

    /**
     * Checks if link is expired.
     *
     * @return true if expired
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }

    /**
     * Checks if click limit is reached.
     *
     * @return true if click limit reached
     */
    public boolean isLimitReached() {
        return maxClicks > 0 && clicksCount >= maxClicks;
    }

    /**
     * Increment click counter.
     */
    public void incrementClick() {
        this.clicksCount++;
    }

    /**
     * Checks if link is active (not expired and not reached limit).
     *
     * @return true if active
     */
    public boolean isActive() {
        return ! isExpired() && ! isLimitReached();
    }
}
