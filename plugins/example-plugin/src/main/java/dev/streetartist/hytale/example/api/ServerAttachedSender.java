package dev.streetartist.hytale.example.api;

/**
 * Implemented by {@link CommandSender}s that know which server they belong to.
 */
public interface ServerAttachedSender extends CommandSender {
    Server server();
}
