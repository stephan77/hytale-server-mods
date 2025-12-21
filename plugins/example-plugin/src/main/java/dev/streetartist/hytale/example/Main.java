package dev.streetartist.hytale.example;

import dev.streetartist.hytale.example.api.Plugin;
import dev.streetartist.hytale.example.api.Server;
import dev.streetartist.hytale.example.runtime.MockServer;

public final class Main {
    public static void main(String[] args) {
        Server server = new MockServer();
        Plugin plugin = new ExamplePlugin();
        plugin.onEnable(server);

        server.logger().info("Server tick simulation...");
        plugin.onDisable(server);
    }
}