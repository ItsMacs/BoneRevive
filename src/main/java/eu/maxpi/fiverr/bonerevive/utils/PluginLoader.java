package eu.maxpi.fiverr.bonerevive.utils;

import eu.maxpi.fiverr.bonerevive.BoneRevive;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class PluginLoader {

    public static HashMap<String, String> lang = new HashMap<>();

    public static HashMap<String, Double> hearts = new HashMap<>();

    public static int crystalData;

    public static void load(){
        BoneRevive.getInstance().saveResource("config.yml", false);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(BoneRevive.getInstance().getDataFolder() + "/config.yml"));
        config.getConfigurationSection("lang").getKeys(false).forEach(s -> {
            lang.put(s, ColorTranslator.translate(config.getString("lang." + s)));
        });

        crystalData = config.getInt("crystal-data");

        YamlConfiguration storage = YamlConfiguration.loadConfiguration(new File(BoneRevive.getInstance().getDataFolder() + "/storage.yml"));
        storage.getKeys(false).forEach(s -> hearts.put(s, storage.getDouble(s)));
    }

    public static void save(){
        YamlConfiguration storage = new YamlConfiguration();

        hearts.forEach(storage::set);

        try {
            storage.save(new File(BoneRevive.getInstance().getDataFolder() + "/storage.yml"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}