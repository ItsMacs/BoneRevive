package eu.maxpi.fiverr.bonerevive.events;

import de.tr7zw.nbtapi.NBTItem;
import eu.maxpi.fiverr.bonerevive.BoneRevive;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class onStand implements Listener {

    @EventHandler
    public void stand(EntitySpawnEvent event){
        if(!(event.getEntity() instanceof ArmorStand stand)) return;

        Player p = stand.getNearbyEntities(3, 3, 3).stream().filter(e -> e instanceof Player).map(e -> (Player)e).filter(p1 -> p1.getInventory().getItemInMainHand().getType() == Material.ARMOR_STAND && p1.getInventory().getItemInMainHand().hasItemMeta()).findAny().orElse(null);
        if(p == null) return;

        ItemStack armor = p.getInventory().getItemInMainHand();
        NBTItem aI = new NBTItem(armor);

        if(!aI.hasKey("pl")) return;
        stand.setMetadata("playerowner", new FixedMetadataValue(BoneRevive.getInstance(), aI.getString("pl")));
        stand.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(0.5D);
        stand.setCustomName(aI.getString("pl") + "'s bone structure");
        stand.setCustomNameVisible(true);
    }

}
