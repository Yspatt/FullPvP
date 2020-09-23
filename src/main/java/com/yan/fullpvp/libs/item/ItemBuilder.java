package com.yan.fullpvp.libs.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder name(String name) {
        change(e -> e.setDisplayName(ChatColor.translateAlternateColorCodes('&', name)));
        return this;
    }

    public ItemBuilder amount(Integer amount) {
        change(e -> itemStack.setAmount(amount));
        return this;
    }

    public ItemBuilder data(Integer data) {
        change(e -> itemStack.setDurability(Short.parseShort(data.toString())));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        change(e -> e.setLore(lore));
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        change(e -> e.addEnchant(enchantment, level, true));
        return this;
    }

    public ItemBuilder flag(ItemFlag flag) {
        change(e -> e.getItemFlags().add(flag));
        return this;
    }

    public ItemStack create() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    private void change(Consumer<ItemMeta> item) {
        item.accept(itemMeta);
    }


}