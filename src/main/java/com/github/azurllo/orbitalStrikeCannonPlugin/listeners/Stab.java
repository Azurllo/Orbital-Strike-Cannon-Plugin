package com.github.azurllo.orbitalStrikeCannonPlugin.listeners;

import com.github.azurllo.orbitalStrikeCannonPlugin.RodKeys;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Stab implements Listener {
    private final NamespacedKey stabKey;
    private final JavaPlugin plugin;

    public Stab(RodKeys rodKeys, JavaPlugin plugin) {
        this.stabKey = rodKeys.stabKey();
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);

        ItemStack brokenItem = event.getBrokenItem();
        if (brokenItem.getType() != Material.FISHING_ROD) return;

        ItemMeta meta = brokenItem.getItemMeta();
        if (meta == null) return;
        if (!meta.getPersistentDataContainer().has(stabKey, PersistentDataType.BYTE)) return;

        Block targetBlock = player.getTargetBlockExact(32768, FluidCollisionMode.NEVER);
        if (targetBlock == null || targetBlock.getType() == Material.AIR) return;

        World.Environment dimension = player.getWorld().getEnvironment();

        if (dimension != World.Environment.NORMAL) {
            return;
        }

        int blockX = targetBlock.getX();
        int blockZ = targetBlock.getZ();
        Location spawnLocation = new Location(player.getWorld(), blockX + 0.5, 320, blockZ + 0.5);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (int i = 0; i < 800; i++) {
                player.getWorld().spawn(spawnLocation, TNTPrimed.class, tnt -> {
                    tnt.setVelocity(new org.bukkit.util.Vector(0, -400, 0));
                    tnt.setFuseTicks(0);
                });
            }
        }, 20L);
    }
}