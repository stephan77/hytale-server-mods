package dev.streetartist.hytale.example.api;

public interface PermissionService {
    boolean hasPermission(CommandSender sender, String permission);
}
