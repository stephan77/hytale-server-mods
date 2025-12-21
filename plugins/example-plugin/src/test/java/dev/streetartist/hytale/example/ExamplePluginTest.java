package dev.streetartist.hytale.example;

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
        MockServer server = new MockServer();
        ExamplePlugin plugin = new ExamplePlugin();
        assertDoesNotThrow(() -> plugin.onEnable(server));
        assertDoesNotThrow(() -> plugin.onDisable(server));
    }

    @Test
    void helloCommandRequiresPermission() {
        MockServer server = new MockServer();
        ExamplePlugin plugin = new ExamplePlugin();
        plugin.onEnable(server);

        MockServer.MockCommandSender sender = server.sender("user-1");

        assertTrue(server.executeCommand("hello", sender));
        assertEquals(1, sender.replies().size());
        assertEquals("You don't have permission: example.hello", sender.replies().get(0));
    }

    @Test
    void helloCommandGreetsWithPermission() {
        MockServer server = new MockServer();
        server.grant("user-2", "example.hello");
        ExamplePlugin plugin = new ExamplePlugin();
        plugin.onEnable(server);

        MockServer.MockCommandSender sender = server.sender("user-2");

        assertTrue(server.executeCommand("hello", sender));
        assertEquals(1, sender.replies().size());
        assertEquals("Hello from example-plugin!", sender.replies().get(0));
    }
}