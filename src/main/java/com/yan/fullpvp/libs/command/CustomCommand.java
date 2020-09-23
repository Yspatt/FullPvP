package com.yan.fullpvp.libs.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yan.fullpvp.libs.command.annotation.CommandAliases;
import com.yan.fullpvp.libs.command.annotation.CommandName;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public abstract class CustomCommand extends Command {


    private HashMap<SubCommand,Consumer<CustomCommand>> subCommandConsumerHashMap = Maps.newHashMap();

    public CustomCommand(){
        super("");
        for (Method m : this.getClass().getDeclaredMethods()) {
            CommandName command = m.getAnnotation(CommandName.class);
            CommandAliases commandAliases =  m.getAnnotation(CommandAliases.class);
            if (command != null) { setName(command.value()); }
            if (commandAliases != null){ setAliases(Arrays.asList(commandAliases.value())); }
        }
    }

    private Player player;

    @Override
    public boolean execute(CommandSender sender, String label, String[] arguments) {
        if (sender instanceof Player){
            this.player = (Player)sender;
        }else{
            this.player = null;
        }
        if (arguments.length > 0){
            subCommandConsumerHashMap.keySet().stream().filter(e -> e.getCommand().equalsIgnoreCase(arguments[0])).findFirst().ifPresent(e ->{
                subCommandConsumerHashMap.get(e).accept(this);
            });
        }
        command(sender,label,arguments);
        return false;
    }

    public abstract void command(CommandSender sender, String label, String[] arguments);

    public Player getPlayer(){ return player; }

    public String getPermissionMessage(){ return ChatColor.RED + "OPS!" + ChatColor.GRAY + " Você não tem permissão."; }

    public void subCommand(String command,Consumer<CustomCommand> consumer){
        subCommandConsumerHashMap.put(new SubCommand(command),consumer);
    }
}
