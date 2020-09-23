package com.yan.fullpvp.data.user;

import com.google.common.collect.Maps;
import com.yan.fullpvp.data.kits.Kit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class User {

    private UUID uuid;

    private Map<Kit,Long> delayKits;

}
