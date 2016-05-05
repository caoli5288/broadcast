package com.mengcraft.broadcast;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mengcraft.broadcast.CollectionUtil.forEach;

/**
 * Created on 16-5-5.
 */
public class BroadcastTask implements Runnable {
    private final Main main;
    private List<Broadcast> list;
    private Iterator<Broadcast> itr;

    public BroadcastTask(Main main) {
        this.main = main;
    }

    public void update() {
        setList(main.fetchList());
        setItr(null);
    }

    public void run() {
        if (itr == null) {
            setItr(list.iterator());
        }
        process();
        processUpdate();
    }

    private void processUpdate() {
        if (!itr.hasNext()) {
            main.getServer().getScheduler().runTaskAsynchronously(main, () -> update());
        }
    }

    private void process() {
        if (itr != null && itr.hasNext()) {
            process(itr.next());
        }
    }

    private void process(Broadcast next) {
        forEach(main.getServer().getOnlinePlayers(), p -> !hasBypassed(next, p), p -> {
            getParsedText(next).forEach(line -> {
                p.sendMessage(line);
            });
        });
        main.getLogger().info(getParsedText(next).toString());
    }

    @SuppressWarnings("unchecked")
    private List<String> getParsedText(Broadcast next) {
        if (next.needParseText()) {
            List<String> parsed = List.class.cast(JSONValue.parse(next.getText()));
            List<String> temp = new ArrayList<>(parsed.size());
            parsed.forEach(line -> {
                temp.add(ChatColor.translateAlternateColorCodes('&', line));
            });
            next.setParsedText(temp);
        }
        return next.getParsedText();
    }

    private boolean hasBypassed(Broadcast next, Player p) {
        return next.getBypass() != null && p.hasPermission(next.getBypass());
    }

    public List<Broadcast> getList() {
        return list;
    }

    public void setList(List<Broadcast> list) {
        this.list = list;
    }

    public Iterator<Broadcast> getItr() {
        return itr;
    }

    public void setItr(Iterator<Broadcast> itr) {
        this.itr = itr;
    }
}
