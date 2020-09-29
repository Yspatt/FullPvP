package com.yan.fullpvp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yan.fullpvp.commands.chat.GlobalChatCommand;
import com.yan.fullpvp.commands.kit.KitCommand;
import com.yan.fullpvp.commands.kit.KitCreatorCommand;
import com.yan.fullpvp.commands.kit.KitDelCommand;
import com.yan.fullpvp.commands.warp.WarpCommand;
import com.yan.fullpvp.commands.warp.WarpDelCommand;
import com.yan.fullpvp.commands.warp.WarpSetCommand;
import com.yan.fullpvp.controller.DatabaseController;
import com.yan.fullpvp.controller.ScoreBoardController;
import com.yan.fullpvp.helpers.CorePlugin;
import com.yan.fullpvp.libs.database.CoreHikariImplement;
import com.yan.fullpvp.libs.file.CoreFile;
import com.yan.fullpvp.libs.scoreboard.ScoreboardManager;
import com.yan.fullpvp.libs.scoreboard.settings.BoardSettings;
import com.yan.fullpvp.libs.scoreboard.settings.ScoreDirection;
import com.yan.fullpvp.listener.PlayerListener;
import com.yan.fullpvp.listener.chat.LocalChatListener;
import com.yan.fullpvp.model.Service;
import com.yan.fullpvp.service.IKitService;
import com.yan.fullpvp.service.IUserService;
import com.yan.fullpvp.service.IWarpService;
import com.yan.fullpvp.service.implement.KitServiceImplement;
import com.yan.fullpvp.service.implement.UserServiceImplement;
import com.yan.fullpvp.service.implement.WarpServiceImplement;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.sql.SQLException;

public final class Main extends CorePlugin {

    @Getter
    private Gson gson;

    @Getter
    private CoreHikariImplement hikariSource;

    @Getter
    private static Main instance;

    @Getter
    private static CoreFile configuration;

    @Getter
    private ScoreboardManager manager;


    @Override
    public void load() {
        instance = this;
        provideService(IUserService.class,new UserServiceImplement());
        provideService(IKitService.class,new KitServiceImplement());
        provideService(IWarpService.class,new WarpServiceImplement());

    }

    @Override
    public void enable() {
        configuration = new CoreFile(this,"config.yml");
        gson = new GsonBuilder().create();
        hikariSource = new CoreHikariImplement(getDataSourceFromConfig());

        DatabaseController.init();
        manager = new ScoreboardManager(this, BoardSettings.builder().boardProvider(new ScoreBoardController()).scoreDirection(ScoreDirection.UP).build());

        for (RegisteredServiceProvider<?> service : getServices()) { if (service instanceof Service){ ((Service)service).init(); } }

        commands(this,
                new KitCommand(),
                new KitCreatorCommand(),
                new KitDelCommand(),

                new WarpCommand(),
                new WarpSetCommand(),
                new WarpDelCommand(),

                new GlobalChatCommand()

        );
        listeners(this,new PlayerListener(),new LocalChatListener());

    }

    @Override
    public void disable() throws SQLException {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("§cServidor reiniciando, voltamos já!"));
        for (RegisteredServiceProvider<?> service : getServices()) { if (service instanceof Service){ ((Service)service).stop(); } }
        hikariSource.connection().close();
    }
}
