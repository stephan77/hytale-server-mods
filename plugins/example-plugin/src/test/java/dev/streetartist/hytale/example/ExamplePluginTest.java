package dev.streetartist.hytale.example;

import dev.streetartist.hytale.example.api.Server;
import dev.streetartist.hytale.example.runtime.MockServer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ExamplePluginTest {

    @Test
    void pluginHasStableId() {
        assertEquals("example-plugin", new ExamplePlugin().id());
    }

    @Test
    void enableDoesNotCrash() {
        Server server = new MockServer();
        ExamplePlugin plugin = new ExamplePlugin();
        assertDoesNotThrow(() -> plugin.onEnable(server));
        assertDoesNotThrow(() -> plugin.onDisable(server));
    }
}