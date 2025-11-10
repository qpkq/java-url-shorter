package com.qpkq.infrastructure.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final public class Configurator {

    /**
     * Configurator instance.
     */
    private static Configurator instance;

    /**
     * HashMap for configs.
     */
    private Map<String, String> configs = new HashMap<>();

    /**
     * Configurator constructor.
     */
    public Configurator() {
        this.configs = this.setConfigs();
    }

    /**
     * Read .env file from root.
     *
     * @return configs.
     */
    private Map<String, String> setConfigs() {
        Map<String, String> envs = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(".env"));

            for (String line : lines) {
                String[] parts = line.split("=");

                if (parts.length == 2) {
                    envs.put(parts[0], parts[1]);
                }
            }
        } catch (IOException _) {
            throw new RuntimeException("No .env file");
        }

        return envs;
    }

    /**
     * Get Env Value by Key.
     *
     * @param key env key.
     * @return env value by key.
     */
    public static String get(String key) {
        if (instance == null) {
            instance = new Configurator();
        }

        if (instance.configs.get(key) == null) {
            throw new RuntimeException("No config key: " + key);
        }

        return instance.configs.get(key);
    }
}
