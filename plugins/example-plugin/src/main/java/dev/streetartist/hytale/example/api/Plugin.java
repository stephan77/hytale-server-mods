package dev.streetartist.hytale.example.api;

public interface Plugin {
    String id();
    void onEnable(Server server);
    void onDisable(Server server);
}