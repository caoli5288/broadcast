package com.mengcraft.broadcast;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created on 16-5-5.
 */
public class Commander implements CommandExecutor {
    private final Main main;
    private BroadcastTask task;

    public Commander(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("/broadcast reload");
        } else if (args[0].equals("reload")) {
            task.update();
        }
        return false;
    }

    public void setTask(BroadcastTask task) {
        this.task = task;
    }
}
