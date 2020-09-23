package com.yan.fullpvp.data.warps;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Warp {

    private UUID id;
    private String name;
    private Location location;
    
    
}
