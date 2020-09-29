package com.yan.fullpvp.commands.kit;

import com.google.common.collect.Maps;
import com.yan.fullpvp.Main;
import com.yan.fullpvp.data.kits.Kit;
import com.yan.fullpvp.data.user.User;
import com.yan.fullpvp.libs.command.CustomCommand;
import com.yan.fullpvp.libs.command.annotation.CommandName;
import com.yan.fullpvp.libs.util.time.TimeUtils;
import com.yan.fullpvp.service.IKitService;
import com.yan.fullpvp.service.IUserService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand extends CustomCommand {

    @CommandName("kit")
    public void command(CommandSender sender, String label, String[] arguments) {
        if (sender instanceof Player) {

            if (arguments.length == 0) {
                sender.sendMessage("§cUse /" + label + " (kit)");
                return;
            }
            Player player = (Player) sender;

            IUserService userService = Main.getInstance().getService(IUserService.class);

            User user = userService.get(player.getUniqueId());

            IKitService kitService = Main.getInstance().getService(IKitService.class);

            Kit kit = kitService.get(arguments[0]);
            if (kit == null) {
                sender.sendMessage(Main.getConfiguration().getStringCache("messages.kit-not-exists"));
                return;
            }

            if (!player.hasPermission("fullpvp.kits.kit." + kit.getName())){
                player.sendMessage(Main.getConfiguration().getStringCache("messages.no-permission"));
                return;
            }

            if (user.getDelayKits().keySet().stream().anyMatch(e -> e.equals(kit.getName()))) {
                if (System.currentTimeMillis() < user.getDelayKits().get(kit.getName())) {
                    sender.sendMessage(Main.getConfiguration().getStringCache("messages.kit-delay").replace("{time}",TimeUtils.timeLeft(user.getDelayKits().get(kit.getName()) - System.currentTimeMillis())));
                    return;
                }
            }
            if (kitService.give(kit,player)){
                user.getDelayKits().put(kit.getName(), System.currentTimeMillis() + kit.getDelay());
                sender.sendMessage(Main.getConfiguration().getStringCache("messages.kit-receive").replace("{kit}",kit.getName()));
            }
        }
    }
}
