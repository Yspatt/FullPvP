package com.yan.fullpvp.libs.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

    public static boolean compare(ItemStack[] less, ItemStack[] more) {
        return slots(more, true) >= slots(less, false);
    }

    public static int slots(ItemStack[] inventory, boolean free) {
        int i = 0;

        for (ItemStack item : inventory) {
            if (item == null || item.getType() == Material.AIR) i++;
        }

        return free ? i : inventory.length - i;
    }


}
