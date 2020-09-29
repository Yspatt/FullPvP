package com.yan.fullpvp.libs.file;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.yan.fullpvp.libs.util.message.MessageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class CoreFile {

    private File file;
    private FileConfiguration fileConfiguration;
    String fileName;

    private Map<String, Object> cache = Maps.newHashMap();

    public CoreFile(final Plugin plugin, String fileName) {
        this(plugin, null, fileName);
    }

    public CoreFile(Plugin plugin, String folder, String fileName) {
        this.fileName = fileName;
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        if (!fileName.isEmpty()) {
            fileName = (fileName.endsWith(".yml") ? fileName : (fileName + ".yml"));
        }

        new File(plugin.getDataFolder() + (folder == null ? "" : File.separator + folder)).mkdirs();

        if (folder == null) {
            this.file = new File(plugin.getDataFolder(), fileName.isEmpty() ? "config.yml" : fileName);
        } else {
            this.file = new File(plugin.getDataFolder(), fileName.isEmpty() ? "config.yml" : folder + File.separator + fileName);
        }

        try {
            if (!file.exists()) {
                if (folder == null) {
                    plugin.saveResource(fileName, false);
                } else {
                    if (!this.file.exists()) {
                        createNewFile();
                    }
                }
            }
            this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
            this.fileConfiguration.loadFromString(Files.toString(file, Charset.forName("UTF-8")));
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
        loadCache();
    }

    public void createNewFile() throws IOException {
        this.file.createNewFile();
    }

    public Object get(final String path, Object defaultValue) {
        Object object = this.fileConfiguration.get(path);

        return object == null ? defaultValue : object;
    }

    public Object get(final String path) {
        return this.get(path, null);
    }

    public Long getLong(final String path){
        return (long) this.get(path, 0L);
    }

    public String getString(final String path) {
        String value = (String) get(path, "");
        if (value == null || value.isEmpty()) return value;
        return new MessageBuilder(value, true).build();
    }

    public int getInt(final String path) {
        return (int) this.get(path, 0);
    }

    public List<?> getList(final String path) {
        return (List<?>) this.get(path, Lists.newArrayList());
    }

    public List<String> getStringList(final String path) {
        List<String> list = Lists.newArrayList();
        for (String s : (List<String>) this.get(path, Lists.newArrayList())) {
            list.add(new MessageBuilder(s, true).build());
        }
        return list;
    }

    public EntityType getEntityType(final String path) {
        return EntityType.valueOf(getString(path));
    }

    public double getDouble(final String path) {
        return (double) this.get(path, 0.0);
    }

    public float getFloat(final String path) {
        return (float) this.get(path, 0.0);
    }

    public List<Integer> getIntegetList(final String path) {
        return (List<Integer>) this.get(path, Lists.newArrayList());
    }

    public List<Double> getDoubleList(final String path) {
        return (List<Double>) this.get(path, Lists.newArrayList());
    }

    public List<Float> getFloatList(final String path) {
        return (List<Float>) this.get(path, Lists.newArrayList());
    }

    public boolean getBoolean(final String path) {
        return (Boolean) this.get(path, false);
    }

    public void set(final String path, final Object value) {
        this.fileConfiguration.set(path, value);
    }

    public ConfigurationSection getConfigurationSection(final String path) {
        return this.fileConfiguration.getConfigurationSection(path);
    }

    public boolean contains(final String path) {
        return this.get(path) != null;
    }

    public void setLocation(String path, Location location) {
        if (location == null) {
            this.set(path, null);
            return;
        }
        String locationString = location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ() + ";" + location.getYaw() + ";" + location.getPitch();
        this.set(path, locationString);
    }

    public Location getLocation(String path) {
        String locationString = (String) this.get(path, null);

        if (locationString == null) {
            return null;
        }

        String[] locationSplit = locationString.split(";");

        String world = locationSplit[0];
        int x = Integer.valueOf(locationSplit[1]);
        int y = Integer.valueOf(locationSplit[2]);
        int z = Integer.valueOf(locationSplit[3]);
        float yaw = Float.valueOf(locationSplit[4]);
        float pitch = Float.valueOf(locationSplit[5]);

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public void save() {
        try {
            this.fileConfiguration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadCache() {
        for (String s : getConfigurationSection("").getKeys(true)) {
            cache.put(s, get(s));
        }
    }

    public String getStringCache(String path){ return this.getCache(path).toString().replace("&","§");}
    public Boolean getBooleanCache(String path){ return Boolean.valueOf(this.getCache(path).toString());}
    public Integer getIntegerCache(String path){ return Integer.valueOf(this.getCache(path).toString());}
    public List<String> getStringListCache(String path){ return (List<String>) this.cache.get(path);}
    public Object getCache(String path) {
        return this.cache.get(path);
    }

}