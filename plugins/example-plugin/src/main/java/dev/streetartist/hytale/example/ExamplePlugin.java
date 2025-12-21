package dev.streetartist.hytale.example;

import dev.streetartist.hytale.example.api.Plugin;
import dev.streetartist.hytale.example.api.Server;

public final class ExamplePlugin implements Plugin {

    @Override
    public String id() {
        return "example-plugin";
    }

    @Override
    public void onEnable(Server server) {
        server.logger().info("Enabling " + id());
        server.commands().register("hello", (sender, args) ->
                sender.reply("Hello from " + id() + "!")
        );
    }

    @Override
    public void onDisable(Server server) {
        server.logger().info("Disabling " + id());
    }
}