package com.yan.fullpvp.service.implement;

import com.google.common.collect.Lists;
import com.yan.fullpvp.Main;
import com.yan.fullpvp.data.kits.Kit;
import com.yan.fullpvp.data.warps.Warp;
import com.yan.fullpvp.libs.file.CoreFile;
import com.yan.fullpvp.libs.location.LocationSerializer;
import com.yan.fullpvp.service.IWarpService;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.UUID;

public class WarpServiceImplement implements IWarpService {

    public List<Warp> warps = Lists.newArrayList();
    public CoreFile config = new CoreFile(Main.getInstance(),"warps.yml");
    @Override
    public Warp get(String name) {
        return warps.stream().filter(warp -> warp.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public Warp create(String name) {
        Warp warp = new Warp(name);
        all().add(warp);
        return warp;
    }

    @Override
    public void delete(Warp warp) {
        config.set("warps." + warp.getId().toString(),null);
        config.save();
        all().remove(warp);
    }

    @Override
    public Warp get(UUID id) {
        return warps.stream().filter(warp -> warp.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Warp> all() {
        return warps;
    }

    @Override
    public void init() {
        ConfigurationSection section = config.getConfigurationSection("warps");
        if (section == null)return;
        for (String string : section.getKeys(false)){
            String path = "warps." + string;
            Warp warp = new Warp(UUID.fromString(string),config.getString(path + ".name"));
            warp.setLocation(config.getLocation(path + ".location"));
            warps.add(warp);
        }
    }

    @Override
    public void stop() {
        for (Warp warp : all()) {
            config.set("warps." + warp.getId().toString() + ".name",warp.getName());
            config.set("warps." + warp.getId().toString() + ".location", warp.getLocation());
        }
        config.save();
    }
}
