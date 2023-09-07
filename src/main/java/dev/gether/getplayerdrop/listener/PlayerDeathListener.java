package dev.gether.getplayerdrop.listener;

import dev.gether.getplayerdrop.GetPlayerDrop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PlayerDeathListener implements Listener {

    private final GetPlayerDrop plugin;

    public PlayerDeathListener(GetPlayerDrop plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();

        event.setKeepInventory(true);
        event.getDrops().clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getDisableItemManager().dropPlayerItems(player);
            }
        }.runTask(plugin);
    }
}
