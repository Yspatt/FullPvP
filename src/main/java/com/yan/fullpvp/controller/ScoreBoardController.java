package com.yan.fullpvp.controller;

import com.google.common.collect.Lists;
import com.yan.fullpvp.Main;
import com.yan.fullpvp.libs.scoreboard.provider.CustomScoreboard;
import com.yan.fullpvp.libs.util.message.MessageBuilder;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.List;

public class ScoreBoardController implements CustomScoreboard {

    @Override
    public String getTitle(Player player) {
        return Main.getConfiguration().getStringCache("scoreboard.title");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = Lists.newArrayList();
        for (String line : Main.getConfiguration().getStringListCache("scoreboard.lines")){
            MessageBuilder builder = new MessageBuilder(line);
            builder.add("%player%",player.getName());
            builder.add("%test%","" +player.getWorld().getTime());
            lines.add(PlaceholderAPI.setPlaceholders(player,builder.build()));
        }


        return lines;
    }
}
