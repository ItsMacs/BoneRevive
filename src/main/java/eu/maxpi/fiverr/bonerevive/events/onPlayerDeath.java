package eu.maxpi.fiverr.bonerevive.events;

import eu.maxpi.fiverr.bonerevive.BoneRevive;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class onPlayerDeath implements Listener {

    @EventHandler
    public void playerDeath(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)) return;
        if(!(event.getEntity() instanceof Player player)) return;

        if(player.getHealth() - event.getFinalDamage() > 0) return;

        player.getWorld().dropItem(player.getLocation(), BoneRevive.getBone(player.getName()));
    }

}
