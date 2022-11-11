package eu.maxpi.fiverr.bonerevive.events;

import eu.maxpi.fiverr.bonerevive.BoneRevive;
import eu.maxpi.fiverr.bonerevive.utils.PluginLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class onInteract implements Listener {

    @EventHandler
    public void interact(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(!event.getItem().isSimilar(BoneRevive.getCrystal())) return;

        Inventory inv = Bukkit.createInventory(null, 54, PluginLoader.lang.get("gui-name"));

        List<String> names = PluginLoader.hearts.keySet().stream().filter(s -> PluginLoader.hearts.get(s) <= 0).toList();

        for (int i = 0; i < 54; i++){
            if(i >= names.size()) break;

            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta)skull.getItemMeta();
            meta.setDisplayName(names.get(i));
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(names.get(i)));
            skull.setItemMeta(meta);

            inv.setItem(i, skull);
        }

        event.getPlayer().openInventory(inv);
    }

}
