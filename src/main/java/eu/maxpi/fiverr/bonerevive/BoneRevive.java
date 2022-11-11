package eu.maxpi.fiverr.bonerevive;

import de.tr7zw.nbtapi.NBTItem;
import eu.maxpi.fiverr.bonerevive.events.*;
import eu.maxpi.fiverr.bonerevive.utils.PluginLoader;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

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
        loadRecipes();

        Bukkit.getLogger().info("BoneRevive by fiverr.com/macslolz was enabled successfully!");
    }

    private void loadCommands(){

    }

    private void loadEvents(){
        Bukkit.getPluginManager().registerEvents(new onInteract(), this);
        Bukkit.getPluginManager().registerEvents(new onInvClick(), this);
        Bukkit.getPluginManager().registerEvents(new onCraft(), this);
        Bukkit.getPluginManager().registerEvents(new onPlayerDeath(), this);
        Bukkit.getPluginManager().registerEvents(new onEntityDamage(), this);
        Bukkit.getPluginManager().registerEvents(new onStand(), this);
    }

    private void loadTasks(){
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if(!PluginLoader.hearts.containsKey(p.getName())) PluginLoader.hearts.put(p.getName(), 20D);

                    double hearts = PluginLoader.hearts.get(p.getName());
                    if(hearts <= 0){
                        p.kickPlayer(PluginLoader.lang.get("no-hearts"));
                        Bukkit.getBanList(BanList.Type.NAME).addBan(p.getName(), PluginLoader.lang.get("no-hearts"), new Date(Long.MAX_VALUE), "CONSOLE");
                        Bukkit.getOnlinePlayers().forEach(p1 -> p1.sendMessage(PluginLoader.lang.get("banned").replace("%player%", p.getName())));
                        return;
                    }

                    p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(PluginLoader.hearts.get(p.getName()));
                });
            }
        }.runTaskTimer(this, 0L, 60L);
    }

    public static ItemStack getBone(String player){
        ItemStack item = new ItemStack(Material.BONE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PluginLoader.lang.get("bone-name").replace("%player%", player));
        item.setItemMeta(meta);

        NBTItem i = new NBTItem(item);
        i.setString("pl", player);

        return i.getItem();
    }

    public static ItemStack getStand(String player){
        ItemStack item = new ItemStack(Material.ARMOR_STAND);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PluginLoader.lang.get("stand-name").replace("%player%", player));
        item.setItemMeta(meta);

        NBTItem i = new NBTItem(item);
        i.setString("pl", player);

        return i.getItem();
    }

    public static ItemStack getCrystal(){
        ItemStack item = new ItemStack(Material.HEART_OF_THE_SEA);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PluginLoader.lang.get("crystal-name"));
        meta.setCustomModelData(1);
        item.setItemMeta(meta);
        return item;
    }

    private void loadRecipes(){
        ShapedRecipe crystal = new ShapedRecipe(new NamespacedKey(this, "crystal"), getCrystal());

        crystal.shape("DND", "NCN", "DND");
        crystal.setIngredient('D', Material.DIAMOND_BLOCK);
        crystal.setIngredient('N', Material.NETHERITE_INGOT);
        crystal.setIngredient('C', Material.END_CRYSTAL);

        Bukkit.addRecipe(crystal);
    }

    @Override
    public void onDisable() {
        PluginLoader.save();

        Bukkit.getLogger().info("BoneRevive by fiverr.com/macslolz was disabled successfully!");
    }
}
