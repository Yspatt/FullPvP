package com.yan.fullpvp.commands.warp;

import com.yan.fullpvp.Main;
import com.yan.fullpvp.data.warps.Warp;
import com.yan.fullpvp.libs.command.CustomCommand;
import com.yan.fullpvp.libs.command.annotation.CommandName;
import com.yan.fullpvp.service.IWarpService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand extends CustomCommand {

    @CommandName("warp")
    public void command(CommandSender sender, String label, String[] arguments) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (arguments.length == 0){
                player.sendMessage("§cUse /" + label + " (warp)");
                return;
            }
            Warp warp = Main.getInstance().getService(IWarpService.class).get(arguments[0]);
            if (warp == null){
                sender.sendMessage(Main.getConfiguration().getStringCache("messages.warp-not-exists").replace("{warp}",arguments[0]));
                return;
            }
            player.teleport(warp.getLocation());
            player.sendMessage(Main.getConfiguration().getStringCache("messages.warp-teleport").replace("{warp}",warp.getName()));
        }
    }
}
