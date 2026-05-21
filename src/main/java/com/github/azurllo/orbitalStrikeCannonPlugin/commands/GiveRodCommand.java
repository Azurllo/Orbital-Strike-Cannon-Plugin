package com.github.azurllo.orbitalStrikeCannonPlugin.commands;

import com.github.azurllo.orbitalStrikeCannonPlugin.RodKeys;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class GiveRodCommand implements CommandExecutor {

    private final RodKeys rodKeys;

    public GiveRodCommand(RodKeys rodKeys) {
        this.rodKeys = rodKeys;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("Use: /giverod <nuke|stab>");
            return true;
        }

        String type = args[0].toLowerCase();
        if (!type.equals("nuke") && !type.equals("stab")) {
            player.sendMessage("Use: /giverod <nuke|stab>");
            return true;
        }

        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = rod.getItemMeta();
        if (meta == null) return true;

        if (type.equals("nuke")) {
            meta.getPersistentDataContainer().set(rodKeys.nukeKey(), PersistentDataType.BYTE, (byte) 1);
            meta.displayName(Component.text("Nuke shot"));
        } else {
            meta.getPersistentDataContainer().set(rodKeys.stabKey(), PersistentDataType.BYTE, (byte) 1);
            meta.displayName(Component.text("Stab shot"));
        }

        if (meta instanceof Damageable damageable) {
            damageable.setDamage(60);
        }

        rod.setItemMeta(meta);
        player.getInventory().addItem(rod);
        return true;
    }
}