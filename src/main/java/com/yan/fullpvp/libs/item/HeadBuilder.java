package com.yan.fullpvp.libs.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.function.Consumer;

public class HeadBuilder {

    private ItemStack itemStack;
    private SkullMeta skullMeta;

    public HeadBuilder() {
        this.itemStack = new ItemBuilder(Material.SKULL_ITEM).data(3).create();
        this.skullMeta = (SkullMeta) itemStack.getItemMeta();
    }

    public HeadBuilder owner(String name) {
        change(e -> e.setOwner(name));
        return this;
    }

    public HeadBuilder url(String url) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        return this;
    }

    public ItemStack create() {
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }


    private void change(Consumer<SkullMeta> skullMetaConsumer) {
        skullMetaConsumer.accept(skullMeta);
    }

}
