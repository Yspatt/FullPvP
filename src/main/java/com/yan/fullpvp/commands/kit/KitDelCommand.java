package com.yan.fullpvp.commands.kit;

import com.yan.fullpvp.Main;
import com.yan.fullpvp.data.kits.Kit;
import com.yan.fullpvp.libs.command.CustomCommand;
import com.yan.fullpvp.libs.command.annotation.CommandName;
import com.yan.fullpvp.service.IKitService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class KitDelCommand extends CustomCommand {

    @CommandName("delkit")
    public void command(CommandSender sender, String label, String[] arguments) {
        if (!sender.hasPermission("fullpvp.kits.delkit")){
            sender.sendMessage(getPermissionMessage());
            return;
        }
        if (sender instanceof Player) {
            Player player = (Player)sender;

            if (arguments.length == 0) {
                sender.sendMessage("§c/" + label + " (kit)");
                return;
            }

            Kit kit = Main.getInstance().getService(IKitService.class).get(arguments[0]);
            if (kit == null){
                player.sendMessage("§cO kit §f'" + arguments[0] + "'§c não existe!");
                return;
            }
            Main.getInstance().getService(IKitService.class).delete(kit);
            player.sendMessage("§aVocê deletou o kit §f" + kit.getName() +"§a com sucesso!");
        }
    }
}
