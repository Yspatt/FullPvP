package com.yan.fullpvp.commands.kit;

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
                sender.sendMessage("§cEste kit não existe.");
                return;
            }
            if (user.getDelayKits().keySet().stream().anyMatch(e -> e.equals(kit))) {
                if (System.currentTimeMillis() <= user.getDelayKits().get(kit)) {
                    sender.sendMessage("§cVocê precisa aguardar " + TimeUtils.timeLeft(user.getDelayKits().get(kit)) + "§c para pegar o kit novamente!");
                    return;
                }

                user.getDelayKits().put(kit, System.currentTimeMillis() + kit.getDelay());
                sender.sendMessage("§aVocê pegou o kit §f'" + kit.getName() + "'§a com sucesso.");
            }
        }
    }
}
