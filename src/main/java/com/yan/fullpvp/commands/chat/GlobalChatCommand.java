package com.yan.fullpvp.commands.chat;

import com.yan.fullpvp.Main;
import com.yan.fullpvp.libs.command.CustomCommand;
import com.yan.fullpvp.libs.command.annotation.CommandAliases;
import com.yan.fullpvp.libs.command.annotation.CommandName;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlobalChatCommand extends CustomCommand {

    @CommandName("g")
    @CommandAliases("global")
    public void command(CommandSender sender, String label, String[] arguments) {
        if (arguments.length == 0){
            sender.sendMessage("§cUse /" + label + " (mensagem)");
            return;
        }
        if (sender instanceof Player){
            Player player = (Player) sender;
            String message = PlaceholderAPI.setBracketPlaceholders(player, Main.getConfiguration().getStringCache("config.global-chat-format").replace("%player%",player.getName()).replace("%message%",getMessage(arguments)));
            String customMessage = player.hasPermission("fullpvp.chat.spotlight") && Main.getConfiguration().getBooleanCache("config.global-chat-spotlight") ? "\n " + message + "\n " : message;
            Bukkit.broadcastMessage(customMessage);
        }else{
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(Main.getConfiguration().getStringCache("config.console-chat-format").replace("%message%",getMessage(arguments)));
            Bukkit.broadcastMessage("");
        }
    }


    public String getMessage(String[] args){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }
        return builder.toString();
    }
}
