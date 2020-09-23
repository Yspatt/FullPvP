package com.yan.fullpvp.data.mines;

import com.yan.fullpvp.libs.util.world.Cuboid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Mine {

    private UUID id;

    private String name;
    private Location position1,position2;
    private Cuboid cuboid;

    private Map<Double, ItemStack> blocks;

    public Mine(){
        cuboid = new Cuboid(position1,position2);
    }

    public void reset(){
        for (Block block : cuboid.getBlocks()) {
            blocks.forEach((key,value) ->{
                Double random = new Random().nextDouble();
                if (random < key){
                    block.setType(value.getType());
                    block.setData(value.getData().getData());
                }
            });
        }
    }

}
