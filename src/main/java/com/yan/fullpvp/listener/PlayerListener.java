package com.yan.fullpvp.listener;

import com.yan.fullpvp.Main;
import com.yan.fullpvp.service.IUserService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event){
        Main.getInstance().getService(IUserService.class).load(event.getPlayer().getUniqueId());
    }
}
