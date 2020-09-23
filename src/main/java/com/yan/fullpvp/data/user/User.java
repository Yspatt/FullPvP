package com.yan.fullpvp.data.user;

import com.google.common.collect.Maps;
import com.yan.fullpvp.data.kits.Kit;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data public class User {

    private UUID uuid;

    private Map<Kit,Long> delayKits;

    public User(UUID uuid){
        this.uuid = uuid;
        this.delayKits = Maps.newHashMap();
    }

}
