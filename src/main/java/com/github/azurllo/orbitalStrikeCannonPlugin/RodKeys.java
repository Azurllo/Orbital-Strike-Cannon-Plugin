package com.github.azurllo.orbitalStrikeCannonPlugin;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class RodKeys {
    private final NamespacedKey nukeKey;
    private final NamespacedKey stabKey;

    public RodKeys(JavaPlugin plugin) {
        this.nukeKey = new NamespacedKey(plugin, "nuke_rod");
        this.stabKey = new NamespacedKey(plugin, "stab_rod");
    }

    public NamespacedKey nukeKey() {
        return nukeKey;
    }

    public NamespacedKey stabKey() {
        return stabKey;
    }
}