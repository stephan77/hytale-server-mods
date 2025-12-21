package dev.streetartist.hytale.example.api;


public interface CommandSender {
    void reply(String message);

    default boolean hasPermission(String permission) {
        if (this instanceof ServerAttachedSender attached) {
            return attached.server().permissions().hasPermission(this, permission);
        }
        return false;
    }
}
