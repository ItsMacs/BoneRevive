package eu.maxpi.fiverr.bonerevive;

import eu.maxpi.fiverr.bonerevive.events.onCraft;
import eu.maxpi.fiverr.bonerevive.events.onPlayerDeath;
import eu.maxpi.fiverr.bonerevive.utils.PluginLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class BoneRevive extends JavaPlugin {

    private static BoneRevive instance = null;

    public static BoneRevive getInstance(){
        return BoneRevive.instance;
    }


    private static void setInstance(BoneRevive in){
        BoneRevive.instance = in;
    }

    @Override
    public void onEnable() {
        setInstance(this);

        PluginLoader.load();

        loadCommands();
        loadEvents();
        loadTasks();

        Bukkit.getLogger().info("BoneRevive by fiverr.com/macslolz was enabled successfully!");
    }

    private void loadCommands(){

    }

    private void loadEvents(){
        Bukkit.getPluginManager().registerEvents(new onCraft(), this);
        Bukkit.getPluginManager().registerEvents(new onPlayerDeath(), this);
    }

    private void loadTasks(){

    }

    public static ItemStack getBone(String player){
        ItemStack item = new ItemStack(Material.BONE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PluginLoader.lang.get("bone-name").replace("%player%", player));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void onDisable() {
        PluginLoader.save();

        Bukkit.getLogger().info("BoneRevive by fiverr.com/macslolz was disabled successfully!");
    }
}
