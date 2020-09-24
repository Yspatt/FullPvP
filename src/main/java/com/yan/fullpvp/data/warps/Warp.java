package com.yan.fullpvp.data.warps;

import lombok.Data;
import org.bukkit.Location;

import java.util.UUID;

@Data
public class Warp {

    private UUID id;
    private String name;
    private Location location;

    public Warp(UUID id,String name){
        this.id = id;
        this.name = name;
    }

    public Warp(String name){
        this.name = name;
        this.id = UUID.randomUUID();
    }
}
