package com.qpkq;

import com.qpkq.cli.Cli;
import com.qpkq.infrastructure.config.Configurator;

public class Application implements Runnable {

    /**
     * Cli instance.
     */
    private final Cli cli;

    /**
     * Application constructor.
     */
    public Application() {
        new Configurator();

        this.cli = new Cli();
    }

    /**
     * Entrance point.
     */
    public static void main(String[] args) {
        new Application().run();
    }

    /**
     * Runs this Application.
     */
    @Override
    public void run() {
        this.cli.start();
    }
}
