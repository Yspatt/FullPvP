package com.yan.fullpvp.commands.kit;

import com.google.common.collect.Lists;
import com.yan.fullpvp.Main;
import com.yan.fullpvp.data.kits.Kit;
import com.yan.fullpvp.libs.command.CustomCommand;
import com.yan.fullpvp.libs.command.annotation.CommandName;
import com.yan.fullpvp.libs.util.time.TimeParser;
import com.yan.fullpvp.service.IKitService;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KitCreatorCommand extends CustomCommand {

    @CommandName("createkit")
    public void command(CommandSender sender, String label, String[] arguments) {
        if (!sender.hasPermission("create.kit")){
            sender.sendMessage(getPermissionMessage());
            return;
        }
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (arguments.length < 2) { sender.sendMessage("§c/" + label + " (nome) (tempo)");return; }

            if (player.getInventory().getContents().length <= 0){
                player.sendMessage("§cVocê deve ter pelo menos 1 item em seu inventário.");
                return;
            }

            IKitService service = Main.getInstance().getService(IKitService.class);

            Kit kit = service.get(arguments[0]);
            if (kit != null) {
                player.sendMessage("§cJá existe um kit com este nome.");
                return;
            }
            kit = service.create(arguments[0]);
            kit.setDelay(TimeParser.parseString(arguments[1]));
            kit.setItems(Lists.newArrayList());
            for (ItemStack content : player.getInventory().getContents()) { if (content == null || content.getType() == Material.AIR)continue;kit.getItems().add(content); }
            player.sendMessage("§aVocê criou o kit §f'" + kit.getName() + "' §acom sucesso!");
        }
    }
}
