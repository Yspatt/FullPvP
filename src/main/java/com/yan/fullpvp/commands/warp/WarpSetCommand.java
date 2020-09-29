package com.yan.fullpvp.commands.warp;

import com.yan.fullpvp.Main;
import com.yan.fullpvp.data.warps.Warp;
import com.yan.fullpvp.libs.command.CustomCommand;
import com.yan.fullpvp.libs.command.annotation.CommandName;
import com.yan.fullpvp.service.IWarpService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpSetCommand extends CustomCommand {

    @CommandName("setwarp")
    public void command(CommandSender sender, String label, String[] arguments) {
        if (sender instanceof Player){
            Player player = (Player)sender;
            if (!player.hasPermission("fullpvp.warps.setwarp")){
                player.sendMessage(getPermissionMessage());
                return;
            }

            if (arguments.length == 0){
                player.sendMessage("§cUse /" + label + " (nome)");
            }

            Warp warp = Main.getInstance().getService(IWarpService.class).get(arguments[0]);
            if (warp != null){
                player.sendMessage("§cA warp §f'" + warp.getName() + "'§c já existe!");
                return;
            }
            warp = Main.getInstance().getService(IWarpService.class).create(arguments[0]);
            warp.setLocation(player.getLocation());
            player.sendMessage("§cVocê criou a warp §f" + warp.getName() + "§a com sucesso!");
        }
    }
}
