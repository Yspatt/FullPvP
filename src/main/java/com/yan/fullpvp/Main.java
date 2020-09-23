package com.yan.fullpvp;

import com.yan.fullpvp.helpers.CorePlugin;
import lombok.Getter;

public final class Main extends CorePlugin {

    @Getter
    private static Main instance;

    @Override
    public void enable() {
        instance = this;
    }

    @Override
    public void disable() {

    }

    @Override
    public void load() {

    }
}
