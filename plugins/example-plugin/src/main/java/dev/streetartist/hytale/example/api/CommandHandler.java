package dev.streetartist.hytale.example.api;

@FunctionalInterface
public interface CommandHandler {
    void handle(CommandSender sender, String[] args);
}
