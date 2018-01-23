package com.mengcraft.broadcast;

import com.mengcraft.simpleorm.EbeanHandler;
import com.mengcraft.simpleorm.EbeanManager;
import com.wodogs.mc.mark.Mark;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static com.mengcraft.broadcast.CollectionUtil.forEach;

/**
 * Created on 16-5-5.
 */
public class Main extends JavaPlugin {

    private final Mark mark = Mark.DEFAULT;
    private EbeanHandler dataSource;

    @SneakyThrows
    public void onEnable() {
        saveDefaultConfig();

        dataSource = EbeanManager.DEFAULT.getHandler(this);
        if (dataSource.isNotInitialized()) {
            dataSource.define(Broadcast.class);
            dataSource.initialize();
        }

        dataSource.install();

        BroadcastTask task = new BroadcastTask(this);
        task.update();

        Commander commander = new Commander(this);
        commander.setTask(task);

        getCommand("broadcast").setExecutor(commander);
        getServer().getScheduler().runTaskTimer(this, task, 600, getConfig().getInt("daily"));
    }

    public List<Broadcast> fetchList() {
        List<Broadcast> fetched = dataSource.find(Broadcast.class).findList();
        List<Broadcast> output = new ArrayList<>();
        forEach(fetched, b -> is(b), b -> {
            output.add(b);
        });
        getLogger().info("Fetched " + output.size() + " record.");
        return output;
    }

    private boolean is(Broadcast b) {
        return b.getMark() == null || b.getMark().equals(mark.getMark());
    }
}
