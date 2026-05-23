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

import java.util.concurrent.ThreadLocalRandom;


public class Nuke implements Listener {
    private  final NamespacedKey nukeKey;
    private final JavaPlugin plugin;

    public Nuke(RodKeys rodKeys, JavaPlugin plugin) {
        this.nukeKey = rodKeys.nukeKey();
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
        ItemStack brokenItem = event.getBrokenItem();
        if ( brokenItem.getType() != Material.FISHING_ROD) return;
        ItemMeta meta = brokenItem.getItemMeta();
        if (meta == null) return;
        if (!meta.getPersistentDataContainer().has(nukeKey, PersistentDataType.BYTE)) return;

        Block targetBlock = player.getTargetBlockExact(32768, FluidCollisionMode.NEVER);
        if (targetBlock == null || targetBlock.getType() == Material.AIR) return;
        World.Environment dimension = player.getWorld().getEnvironment();

        if (dimension != World.Environment.NORMAL) {
            return;
        }

        int blockX = targetBlock.getX();
        int blockY = targetBlock.getY();
        int blockZ = targetBlock.getZ();
        Location spawnLocation = new Location(player.getWorld(), blockX + 0.5, blockY+65, blockZ + 0.5);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (int i = 0; i < 10; i++) {
                for (int x = 0; x < 100; x++) {
                    float finalI = i;
                    player.getWorld().spawn(spawnLocation, TNTPrimed.class, tnt -> {
                        float power = (finalI + 1)/(8);
                        org.bukkit.util.Vector currentVelocity = tnt.getVelocity();


                        float vectorDirection = (float) ThreadLocalRandom.current().nextInt(0, 36000) / 100;
                        double xVel = -Math.sin(vectorDirection) * power;
                        double zVel =  Math.cos(vectorDirection) * power;

                        tnt.setVelocity(new org.bukkit.util.Vector(xVel, 0, zVel));
                    });
                }
            }
            player.getWorld().spawn(spawnLocation, TNTPrimed.class, tnt -> {
                org.bukkit.util.Vector currentVelocity = tnt.getVelocity();
                float vectorDirection = (float) ThreadLocalRandom.current().nextInt(0, 36000) / 100;
                double xVel = -Math.sin(vectorDirection) * 0;
                double zVel =  Math.cos(vectorDirection) * 0;

                tnt.setVelocity(new org.bukkit.util.Vector(xVel, 0, zVel));
            });

        }, 20L);

    }
}
