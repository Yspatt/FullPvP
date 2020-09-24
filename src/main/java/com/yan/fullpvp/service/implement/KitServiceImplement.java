package com.yan.fullpvp.service.implement;

import com.google.common.collect.Lists;
import com.yan.fullpvp.Main;
import com.yan.fullpvp.data.kits.Kit;
import com.yan.fullpvp.libs.file.CoreFile;
import com.yan.fullpvp.libs.item.InventoryUtil;
import com.yan.fullpvp.service.IKitService;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
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
        Kit kit = new Kit(name);
        kits.add(kit);
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
            String path = "kits." + string;
            Kit kit = new Kit(file.getString(path + ".name"),UUID.fromString(string));
            kit.setDelay(file.getInt(path + ".delay"));
            List<ItemStack> items = (List<ItemStack>) file.getList(path + ".items");
            kit.setItems(items);

            List<ItemStack> armor = (List<ItemStack>) file.getList(path + ".armor");
            kit.setArmor(armor);

            kits.add(kit);
        }
    }

    @Override
    public void delete(Kit kit) {
        file.set("kits." + kit.getId().toString(),null);
        file.save();
        all().remove(kit);
    }

    @Override
    public void stop() {
        for (Kit kit : all()) {
            file.set("kits." + kit.getId().toString() + ".name",kit.getName());
            file.set("kits." + kit.getId().toString() + ".delay",kit.getDelay());
            file.set("kits." + kit.getId().toString() + ".items",kit.getItems());
            file.set("kits." + kit.getId().toString() + ".armor",kit.getArmor());
        }
        file.save();
    }

    @Override
    public boolean give(Kit kit, Player player){

        if (InventoryUtil.slots(player.getInventory().getContents(),true) < (kit.getItems().size() + kit.getArmor().size())){
            player.sendMessage(Main.getConfiguration().getStringCache("messages.inventory-full"));
            return false;
        }else {
            for (ItemStack item : kit.getItems()) {
                player.getInventory().addItem(item);
            }
            if (kit.getArmor().size() > 0) {
                if (InventoryUtil.slots(player.getInventory().getArmorContents(), false) > 0) {
                    for (ItemStack itemStack : kit.getArmor()) {
                        player.getInventory().addItem(itemStack);
                    }
                } else if (InventoryUtil.slots(player.getInventory().getArmorContents(), false) == 0) {
                    player.getInventory().setHelmet(kit.getArmor().get(3));
                    player.getInventory().setChestplate(kit.getArmor().get(1));
                    player.getInventory().setLeggings(kit.getArmor().get(2));
                    player.getInventory().setBoots(kit.getArmor().get(0));

                }
            }
        }
        return true;

    }
}
