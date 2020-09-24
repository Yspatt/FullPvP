package com.yan.fullpvp.commands.warp;

import com.yan.fullpvp.Main;
import com.yan.fullpvp.data.warps.Warp;
import com.yan.fullpvp.libs.command.CustomCommand;
import com.yan.fullpvp.libs.command.annotation.CommandName;
import com.yan.fullpvp.service.IWarpService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpDelCommand extends CustomCommand {

    @CommandName("delwarp")
    public void command(CommandSender sender, String label, String[] arguments) {
        if (sender instanceof Player){
            Player player = (Player)sender;
            if (!player.hasPermission("fullpvp.delwarp")){
                player.sendMessage(getPermissionMessage());
                return;
            }

            if (arguments.length == 0){
                player.sendMessage("§cUse /" + label + " (warp)");
            }

            Warp warp = Main.getInstance().getService(IWarpService.class).get(arguments[0]);
            if (warp == null){
                player.sendMessage("§cA warp §f'" + arguments[0] + "'§c não existe!");
                return;
            }
            Main.getInstance().getService((IWarpService.class)).delete(warp);
            player.sendMessage("§cVocê deletou a warp §f" + warp.getName() + "§a com sucesso!");
        }
    }
}
