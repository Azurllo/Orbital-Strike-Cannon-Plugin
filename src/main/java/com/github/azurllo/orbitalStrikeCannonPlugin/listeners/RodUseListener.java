package com.github.azurllo.orbitalStrikeCannonPlugin.listeners;

import com.github.azurllo.orbitalStrikeCannonPlugin.RodKeys;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class RodUseListener implements Listener {

    private final RodKeys rodKeys;

    public RodUseListener(RodKeys rodKeys) {
        this.rodKeys = rodKeys;
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        PlayerFishEvent.State state = event.getState();
        Player player = event.getPlayer();
        if (state != PlayerFishEvent.State.FISHING &&
                state != PlayerFishEvent.State.REEL_IN &&
                state != PlayerFishEvent.State.CAUGHT_FISH &&
                state != PlayerFishEvent.State.CAUGHT_ENTITY &&
                state != PlayerFishEvent.State.IN_GROUND) {
            return;
        }

        ItemStack rod = event.getPlayer().getInventory().getItemInMainHand();

        if (rod.getType() != Material.FISHING_ROD) return;

        ItemMeta meta = rod.getItemMeta();
        if (meta == null) return;

        boolean isNuke = meta.getPersistentDataContainer().has(rodKeys.nukeKey(), PersistentDataType.BYTE);
        boolean isStab = meta.getPersistentDataContainer().has(rodKeys.stabKey(), PersistentDataType.BYTE);

        if (!isNuke && !isStab) return;

        rod.damage(2, player);
    }
}