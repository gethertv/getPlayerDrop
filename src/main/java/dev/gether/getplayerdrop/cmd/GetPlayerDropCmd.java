package dev.gether.getplayerdrop.cmd;

import dev.gether.getplayerdrop.GetPlayerDrop;
import dev.gether.getplayerdrop.manager.DisableItemManager;
import dev.gether.getplayerdrop.utils.ColorFixer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GetPlayerDropCmd implements CommandExecutor {

    private final String ADMIN_PERMISSION = "getplayerdrop.admin";

    private final GetPlayerDrop plugin;
    public GetPlayerDropCmd(GetPlayerDrop plugin)
    {
        this.plugin = plugin;
        plugin.getCommand("getplayerdrop").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;

        Player player = (Player) sender;
        if(!player.hasPermission(ADMIN_PERMISSION))
            return false;

        if(args.length==1)
        {
            if(args[0].equalsIgnoreCase("reload"))
            {
                plugin.reloadConfig();
                DisableItemManager disableItemManager = new DisableItemManager(plugin);
                plugin.setDisableItemManager(disableItemManager);
                player.sendMessage(ColorFixer.addColors("&aPomyslnie przeladowano plugin!"));
                return true;
            }
            if(args[0].equalsIgnoreCase("add"))
            {
                ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                if(itemInMainHand==null || itemInMainHand.getType()== Material.AIR)
                {
                    player.sendMessage(ColorFixer.addColors("&cMusisz trzymac item w lapce!"));
                    return false;
                }

                List<Object> list = (List<Object>) plugin.getConfig().getList("disable.items");
                list.add(itemInMainHand);
                plugin.getConfig().set("disable.items", list);
                plugin.saveConfig();
                player.sendMessage(ColorFixer.addColors("&aPomyslnie dodano przedmiot!"));
                return true;

            }
        }
        return false;
    }
}
