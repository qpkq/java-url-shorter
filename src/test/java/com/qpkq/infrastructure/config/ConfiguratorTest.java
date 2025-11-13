package com.qpkq.infrastructure.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguratorTest {

    private static final Path ENV_PATH = Paths.get(".env");
    private static List<String> originalEnvContent;

    @BeforeAll
    static void backupEnv() throws IOException {
        if (Files.exists(ENV_PATH)) {
            originalEnvContent = Files.readAllLines(ENV_PATH);
        }
    }

    @AfterAll
    static void restoreEnv() throws IOException {
        if (originalEnvContent != null) {
            Files.write(ENV_PATH, originalEnvContent);
        } else {
            Files.deleteIfExists(ENV_PATH);
        }
    }

    @BeforeEach
    void clearSingleton() throws Exception {
        var field = Configurator.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    void testReadEnvAndGetValue() throws IOException {
        List<String> lines = List.of("LINK_TTL_HOURS=24", "APP_MODE=dev");
        Files.write(ENV_PATH, lines);

        String value = Configurator.get("LINK_TTL_HOURS");

        assertEquals("24", value);
    }

    @Test
    void testMissingEnvFile() throws Exception {
        Files.deleteIfExists(ENV_PATH);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            Configurator.get("ANY_KEY");
        });

        assertTrue(ex.getMessage().contains("No .env file"));
    }

    @Test
    void testMissingKey() throws IOException {
        Files.write(ENV_PATH, List.of("LINK_TTL_HOURS=12"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            Configurator.get("UNKNOWN_KEY");
        });

        assertTrue(ex.getMessage().contains("No config key"));
    }

    @Test
    void testInvalidLinesIgnored() throws IOException {
        Files.write(ENV_PATH, List.of("INVALID_LINE", "LINK_TTL_HOURS=48"));

        String value = Configurator.get("LINK_TTL_HOURS");

        assertEquals("48", value);
    }
}
