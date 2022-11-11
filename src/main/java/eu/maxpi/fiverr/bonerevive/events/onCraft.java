package eu.maxpi.fiverr.bonerevive.events;

import de.tr7zw.nbtapi.NBTItem;
import eu.maxpi.fiverr.bonerevive.BoneRevive;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class onCraft implements Listener {

    @EventHandler
    public void craftEvent(PrepareItemCraftEvent event){
        List<ItemStack> items = Arrays.asList(event.getInventory().getMatrix()).stream().filter(Objects::nonNull).toList();
        if(items.size() != 4) return;

        if(items.stream().anyMatch(i -> i.getType() != Material.BONE)) return;

        if(items.stream().anyMatch(i -> {
            NBTItem it = new NBTItem(i);
            return !it.hasKey("pl");
        })) return;

        String p = new NBTItem(items.get(0)).getString("pl");
        if(items.stream().anyMatch(i -> {
            NBTItem it = new NBTItem(i);
            return !it.getString("pl").equals(p);
        })) return;

        event.getInventory().setResult(BoneRevive.getStand(p));
    }

}
