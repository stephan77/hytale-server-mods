package dev.streetartist.hytale.example.runtime;

import dev.streetartist.hytale.example.api.*;

import java.util.HashMap;
import java.util.Map;

public final class MockServer implements Server {
    private final Logger logger = new StdoutLogger();
    private final CommandRegistry commands = new InMemoryCommandRegistry(logger);

    @Override public Logger logger() { return logger; }
    @Override public CommandRegistry commands() { return commands; }

    // --- simple in-memory command system for local testing ---
    static final class InMemoryCommandRegistry implements CommandRegistry {
        private final Logger logger;
        private final Map<String, CommandHandler> map = new HashMap<>();

        InMemoryCommandRegistry(Logger logger) { this.logger = logger; }

        @Override
        public void register(String name, CommandHandler handler) {
            if (map.containsKey(name)) {
                logger.warn("Command already registered: " + name);
                return;
            }
            map.put(name, handler);
            logger.info("Registered command: " + name);

            // Demo: run immediately once, like a smoke test
            handler.handle(message -> logger.info("[reply] " + message), new String[0]);
        }
    }

    static final class StdoutLogger implements Logger {
        @Override public void info(String message) { System.out.println("[INFO] " + message); }
        @Override public void warn(String message) { System.out.println("[WARN] " + message); }
        @Override public void error(String message, Throwable t) {
            System.out.println("[ERROR] " + message);
            t.printStackTrace(System.out);
        }
    }
}