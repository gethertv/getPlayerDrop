package dev.gether.getplayerdrop.manager;

import dev.gether.getplayerdrop.GetPlayerDrop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DisableItemManager {


    private final GetPlayerDrop plugin;

    private List<Material> disableMaterial = new ArrayList<>();
    private List<ItemStack> disableItemStack = new ArrayList<>();
    private Random random = new Random(System.currentTimeMillis());

    private double chanceToLostItem = 0;

    public DisableItemManager(GetPlayerDrop plugin)
    {
        this.plugin = plugin;

        this.chanceToLostItem = plugin.getConfig().getDouble("chance-lost-item");
        implementsDisableItems();
    }

    private void implementsDisableItems() {
        ConfigurationSection disableSection = plugin.getConfig().getConfigurationSection("disable");

        assert disableSection != null;
        loadMaterial(disableSection);
        loadItems(disableSection);
    }

    private void loadItems(ConfigurationSection disableSection) {
        List<?> items = disableSection.getList(".items");
        for (Object itemObject : items) {
            ItemStack itemStack = (ItemStack) itemObject;
            disableItemStack.add(itemStack);
        }
    }

    private void loadMaterial(ConfigurationSection disableSection) {
        for(String materialStr : disableSection.getStringList(".material"))
        {
            Material material = Material.valueOf(materialStr.toUpperCase());
            disableMaterial.add(material);
        }
    }

    public void dropPlayerItems(Player player) {

        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if(item==null || item.getType()==Material.AIR)
                continue;

            if(disableMaterial.contains(item.getType()))
                continue;

            if(isItemDisable(item))
                continue;

            double winTicket = random.nextDouble() * 100;
            if (chanceToLostItem < winTicket)
                continue;

            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item);
            inventory.setItem(i, null);

        }
    }

    private boolean isItemDisable(ItemStack item) {
        for (ItemStack itemStack : disableItemStack) {
            if(itemStack.isSimilar(item))
                return true;
        }
        return false;
    }


}
