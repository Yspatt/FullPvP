package com.yan.fullpvp.listener.chat;

import com.yan.fullpvp.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.stream.Collectors;

public class LocalChatListener implements Listener {



    @EventHandler
    private void chat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        event.setCancelled(true);
        int radius = Main.getConfiguration().getIntegerCache("config.local-chat-radius");
        String format = PlaceholderAPI.setPlaceholders(player, Main.getConfiguration().getStringCache("config.local-chat-format").replace("%player%",player.getName()).replace("%message%",event.getMessage()));
        List<Entity> nearby = player.getNearbyEntities(radius,radius,radius).stream().filter(entity -> entity instanceof Player).collect(Collectors.toList());
        player.sendMessage(format);
        if (nearby.size() <= 1){
            player.sendMessage(Main.getConfiguration().getStringCache("messages.nobody-around"));
            return;
        }
        for (Entity entity : nearby) {
            Player target = (Player) entity;
            if (target.equals(player))continue;
            target.sendMessage(format);
        }
    }
}
