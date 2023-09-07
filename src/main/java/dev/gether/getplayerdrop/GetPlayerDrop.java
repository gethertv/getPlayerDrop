package dev.gether.getplayerdrop;

import dev.gether.getplayerdrop.cmd.GetPlayerDropCmd;
import dev.gether.getplayerdrop.listener.PlayerDeathListener;
import dev.gether.getplayerdrop.manager.DisableItemManager;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class GetPlayerDrop extends JavaPlugin {

    // instance
    private static GetPlayerDrop instance;

    // manager
    private DisableItemManager disableItemManager;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        // manager
        disableItemManager = new DisableItemManager(this);


        // listener
        new PlayerDeathListener(this);

        // command
        new GetPlayerDropCmd(this);

    }


    @Override
    public void onDisable() {

        HandlerList.unregisterAll(this);
    }

    public DisableItemManager getDisableItemManager() {
        return disableItemManager;
    }

    public void setDisableItemManager(DisableItemManager disableItemManager) {
        this.disableItemManager = disableItemManager;
    }
}
