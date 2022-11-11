package eu.maxpi.fiverr.bonerevive.events;

import eu.maxpi.fiverr.bonerevive.utils.PluginLoader;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class onInvClick implements Listener {

    @EventHandler
    public void invClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equals(PluginLoader.lang.get("gui-name"))) return;

        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getType() != Material.PLAYER_HEAD);

        String p = event.getCurrentItem().getItemMeta().getDisplayName();
        if(!PluginLoader.hearts.containsKey(p)) return;

        event.getWhoClicked().closeInventory();
        PluginLoader.hearts.put(p, 20D);
        Bukkit.getBanList(BanList.Type.NAME).pardon(p);
        event.getWhoClicked().getInventory().getItemInMainHand().setAmount(event.getWhoClicked().getInventory().getItemInMainHand().getAmount() - 1);
        Bukkit.getOnlinePlayers().forEach(p1 -> p1.sendMessage(PluginLoader.lang.get("unbanned").replace("%player%", p).replace("%unbanner%", event.getWhoClicked().getName())));
    }

}
