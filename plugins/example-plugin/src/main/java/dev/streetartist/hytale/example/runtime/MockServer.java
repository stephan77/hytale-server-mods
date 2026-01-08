package dev.streetartist.hytale.example.runtime;

import dev.streetartist.hytale.example.api.*;

import java.util.*;

public final class MockServer implements Server {
    private final Logger logger = new StdoutLogger();
    private final InMemoryCommandRegistry commands = new InMemoryCommandRegistry(logger);
    private final MockPermissionService permissions = new MockPermissionService();

    @Override public Logger logger() { return logger; }
    @Override public CommandRegistry commands() { return commands; }
    @Override public PermissionService permissions() { return permissions; }

    public MockCommandSender sender(String id) {
        return new MockCommandSender(this, id);
    }

    public void grant(String senderId, String permission) {
        permissions.grant(senderId, permission);
    }

    public boolean executeCommand(String name, CommandSender sender, String... args) {
        return commands.execute(name, sender, args);
    }

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
        }

        boolean execute(String name, CommandSender sender, String[] args) {
            CommandHandler handler = map.get(name);
            if (handler == null) {
                logger.warn("Unknown command: " + name);
                return false;
            }
            handler.handle(sender, args);
            return true;
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

    static final class MockCommandSender implements CommandSender, ServerAttachedSender, IdentifiedSender {
        private final MockServer server;
        private final String id;
        private final List<String> replies = new ArrayList<>();

        MockCommandSender(MockServer server, String id) {
            this.server = server;
            this.id = id;
        }

        @Override
        public void reply(String message) {
            replies.add(message);
            logger().info("[reply] " + message);
        }

        @Override
        public Server server() {
            return server;
        }

        @Override
        public String id() {
            return id;
        }

        public List<String> replies() {
            return List.copyOf(replies);
        }

        private Logger logger() {
            return server.logger();
        }
    }

    interface IdentifiedSender {
        String id();
    }

    final class MockPermissionService implements PermissionService {
        private final Map<String, Set<String>> permissions = new HashMap<>();

        @Override
        public boolean hasPermission(CommandSender sender, String permission) {
            if (!(sender instanceof IdentifiedSender identifiedSender)) {
                return false;
            }
            Set<String> granted = permissions.get(identifiedSender.id());
            return granted != null && (granted.contains("*") || granted.contains(permission));
        }

        void grant(String senderId, String permission) {
            permissions.computeIfAbsent(senderId, ignored -> new HashSet<>()).add(permission);
        }
    }
}