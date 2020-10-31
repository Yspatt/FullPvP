package com.yan.fullpvp.listener;

import com.yan.fullpvp.Main;
import com.yan.fullpvp.data.mines.Mine;
import com.yan.fullpvp.data.user.User;
import com.yan.fullpvp.service.IUserService;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {

    private IUserService userService = Main.getInstance().getService(IUserService.class);

    @EventHandler
    public void join(PlayerJoinEvent event){
        event.setJoinMessage(null);
        userService.load(event.getPlayer().getUniqueId());

    }

    @EventHandler
    public void quit(PlayerQuitEvent event){
        event.setQuitMessage(null);
        User user = userService.get(event.getPlayer().getUniqueId());
        userService.save(user);
    }
}
