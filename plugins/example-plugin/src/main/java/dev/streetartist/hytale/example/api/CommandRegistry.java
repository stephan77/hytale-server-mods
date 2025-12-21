package dev.streetartist.hytale.example.api;

public interface CommandRegistry {
    void register(String name, CommandHandler handler);
}
