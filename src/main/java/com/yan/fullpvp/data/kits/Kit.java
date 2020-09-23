package com.yan.fullpvp.data.kits;

import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

@Data @Builder public class Kit {

    private UUID id;

    private String name;

    private long delay;

    private List<ItemStack> items;

}
