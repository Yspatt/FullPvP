package com.yan.fullpvp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yan.fullpvp.commands.kit.KitCommand;
import com.yan.fullpvp.commands.kit.KitCreatorCommand;
import com.yan.fullpvp.controller.DatabaseController;
import com.yan.fullpvp.helpers.CorePlugin;
import com.yan.fullpvp.libs.database.CoreHikariImplement;
import com.yan.fullpvp.listener.PlayerListener;
import com.yan.fullpvp.service.IKitService;
import com.yan.fullpvp.service.IUserService;
import com.yan.fullpvp.service.implement.KitServiceImplement;
import com.yan.fullpvp.service.implement.UserServiceImplement;
import lombok.Getter;

public final class Main extends CorePlugin {

    @Getter
    private Gson gson;

    @Getter
    private CoreHikariImplement hikariSource;

    @Getter
    private static Main instance;


    @Override
    public void load() {
        instance = this;
        provideService(IUserService.class,new UserServiceImplement());
        provideService(IKitService.class,new KitServiceImplement());

    }

    @Override
    public void enable() {

        gson = new GsonBuilder().create();
        hikariSource = new CoreHikariImplement(getDataSourceFromConfig());

        DatabaseController.init();

        getService(IUserService.class).init();
        getService(IKitService.class).init();

        commands(this,new KitCommand(),new KitCreatorCommand());
        listeners(this,new PlayerListener());

    }

    @Override
    public void disable() {
        getService(IUserService.class).stop();
        getService(IKitService.class).stop();
        try {
            hikariSource.connection().close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
