package com.qpkq.cli;

import com.qpkq.core.models.User;

public class CliContext {

    /**
     * User Instance.
     */
    private User currentUser;

    /**
     * CliContext constructor.
     *
     * @param user instance.
     */
    public CliContext(User user) {
        this.currentUser = user;
    }

    /**
     * Get current User.
     *
     * @return User Instance.
     */
    public User currentUser() {
        return currentUser;
    }

    /**
     * Switch User.
     *
     * @param user instance.
     */
    public void switchUser(User user) {
        this.currentUser = user;
    }
}
