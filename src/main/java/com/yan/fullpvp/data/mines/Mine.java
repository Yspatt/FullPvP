package com.yan.fullpvp.data.mines;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yan.fullpvp.Main;
import com.yan.fullpvp.libs.util.message.MessageBuilder;
import com.yan.fullpvp.libs.util.message.ProgressBar;
import com.yan.fullpvp.libs.util.time.TimeUtils;
import com.yan.fullpvp.libs.util.world.Cuboid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Mine {

    private UUID id;
    private String name;

    private long reset;

    private Location spawn;


    private Location hologramLocation;
    private List<TextLine> textLine = Lists.newArrayList();
    private Hologram hologram;

    private Location position1,position2;
    private Cuboid cuboid;

    private Map<ItemStack,Double> blocks;

    private int blocksBreak;

    public Mine(String name,Location hologramLocation,Location position1,Location position2){
        this.blocks = Maps.newHashMap();
        this.name = name;
        this.position1 = position1;
        this.position2 = position2;
        this.cuboid = new Cuboid(position1,position2);
        this.hologramLocation = hologramLocation;
        this.hologram = HologramsAPI.createHologram(Main.getInstance(),hologramLocation);
    }

    public List<Player> players(){
        List<Player> players = Lists.newArrayList();
        Cuboid cuboid = new Cuboid(position1,position2);
        cuboid = cuboid.expand(Cuboid.CuboidDirection.Up,20);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (cuboid.contains(onlinePlayer.getLocation())){
                players.add(onlinePlayer);
            }
        }
        return players;
    }

    public void update(){
        hologram.clearLines();
        List<String> lines = Main.getConfiguration().getStringListCache("mines.hologram");
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            MessageBuilder builder = new MessageBuilder(line);
            builder.add("%mine%",this.name);
            builder.add("%block_break%","" +this.blocksBreak);
            builder.add("%block_max%","" +this.getBlocks().size());
            builder.add("%progress_bar%", ProgressBar.getProgressBar(this.blocksBreak,this.blocks.size(),
                    Main.getConfiguration().getIntegerCache("mines.progressbar.max-icons"),
                    Main.getConfiguration().getStringCache("mines.progressbar.icon"),
                    Main.getConfiguration().getStringCache("mines.progressbar.completed-color"),
                    Main.getConfiguration().getStringCache("mines.progressbar.not-completed-color")));
            builder.add("%players%","" +players().size());
            builder.add("%reset%",TimeUtils.timeLeft(reset - System.currentTimeMillis()));
            hologram.insertTextLine(i,builder.build().replace("&","§"));

        }
    }

    public void reset(){
        for (Block block : cuboid.getBlocks()) {
            blocks.forEach((key,value) ->{
                Double random = new Random().nextDouble();
                if (random < value){
                    block.setType(key.getType());
                    block.setData(key.getData().getData());
                }
            });
        }
    }

}
