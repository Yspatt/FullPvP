package com.yan.fullpvp.data.kits;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

@Data public class Kit {

    private UUID id;

    private String name;

    private int delay;

    private List<ItemStack> items = Lists.newArrayList();
    private List<ItemStack> armor = Lists.newArrayList();

    public Kit(String name,UUID id){
        this.name = name;
        this.id = id;
    }

    public Kit(String name){
        this.name = name;
        this.id = UUID.randomUUID();
    }

}
