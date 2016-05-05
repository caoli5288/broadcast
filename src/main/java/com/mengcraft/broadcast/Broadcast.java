package com.mengcraft.broadcast;

import org.bukkit.ChatColor;
import org.json.simple.JSONValue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16-5-5.
 */
@Entity
public class Broadcast {
    @Id
    private int id;
    @Column
    private String mark;
    @Column(nullable = false)
    private String text;
    @Column
    private String bypass;
    @Transient
    private List<String> parsedText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBypass() {
        return bypass;
    }

    public void setBypass(String bypass) {
        this.bypass = bypass;
    }

    public List<String> getParsedText() {
        return parsedText;
    }

    public void setParsedText(List<String> parsedText) {
        this.parsedText = parsedText;
    }

    public boolean needParseText() {
        return parsedText == null;
    }
}
