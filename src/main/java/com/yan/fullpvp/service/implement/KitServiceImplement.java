package com.yan.fullpvp.service.implement;

import com.google.common.collect.Lists;
import com.yan.fullpvp.Main;
import com.yan.fullpvp.data.kits.Kit;
import com.yan.fullpvp.libs.file.CoreFile;
import com.yan.fullpvp.service.IKitService;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class KitServiceImplement implements IKitService {

    private List<Kit> kits = Lists.newArrayList();
    public CoreFile file = new CoreFile(Main.getInstance(),"kits.yml");

    @Override
    public Kit get(UUID id) {
        return all().stream().filter(kit -> kit.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Kit get(String name) {
        return all().stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public Kit create(String name) {
        Kit kit = Kit.builder().name(name).build();
        return kit;
    }

    @Override
    public List<Kit> all() {
        return kits;
    }

    @Override
    public void init() {
        ConfigurationSection section = file.getConfigurationSection("kits");
        if (section == null)return;
        for (String string : section.getKeys(false)){
            String path = "Kits." + string;
            Kit kit = create(file.getString(path + ".name"));
            kit.setId(UUID.fromString(string));
            kit.setDelay((Long) file.get(path + ".delay"));
            List<ItemStack> items = (List<ItemStack>) file.getList(path + ".items");
            kit.setItems(items);
        }
    }

    @Override
    public void stop() {
        for (Kit kit : all()) {
            file.set("kits." + kit.getId().toString() + ".name",kit.getName());
            file.set("kits." + kit.getId().toString() + ".delay",kit.getDelay());
            file.set("kits." + kit.getId().toString() + ".items",kit.getItems());
        }
    }
}
