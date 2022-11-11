package eu.maxpi.fiverr.bonerevive.events;

import eu.maxpi.fiverr.bonerevive.utils.PluginLoader;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class onEntityDamage implements Listener {

    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof ArmorStand stand)) return;
        if(!(event.getDamager() instanceof Player p)) return;

        //if(stand.getHealth() - event.getFinalDamage() > 0) return;

        if(!stand.hasMetadata("playerowner")) return;

        stand.setHealth(0);
        PluginLoader.hearts.put(stand.getMetadata("playerowner").get(0).asString(), PluginLoader.hearts.get(stand.getMetadata("playerowner").get(0).asString()) - 2);
        PluginLoader.hearts.put(p.getName(), 2 + PluginLoader.hearts.get(p.getName()));
    }

}
