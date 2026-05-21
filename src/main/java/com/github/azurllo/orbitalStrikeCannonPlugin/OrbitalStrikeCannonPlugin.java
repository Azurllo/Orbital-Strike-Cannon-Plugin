package com.github.azurllo.orbitalStrikeCannonPlugin;

import com.github.azurllo.orbitalStrikeCannonPlugin.commands.GiveRodCommand;
import com.github.azurllo.orbitalStrikeCannonPlugin.listeners.Nuke;
import com.github.azurllo.orbitalStrikeCannonPlugin.listeners.RodUseListener;
import com.github.azurllo.orbitalStrikeCannonPlugin.listeners.Stab;
import org.bukkit.plugin.java.JavaPlugin;

public final class OrbitalStrikeCannonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        RodKeys rodKeys = new RodKeys(this);
        getServer().getPluginManager().registerEvents(new Nuke(rodKeys, this), this);
        getServer().getPluginManager().registerEvents(new Stab(rodKeys, this), this);
        getServer().getPluginManager().registerEvents(new RodUseListener(rodKeys), this);

        if (getCommand("giverod") != null) {
            getCommand("giverod").setExecutor(new GiveRodCommand(rodKeys));
        }
    }
}