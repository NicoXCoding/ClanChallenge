package com.nicocoding;

import org.bukkit.entity.Player;

public class Clan {

    private String name;
    private Player owner;

    public Clan(String name, Player owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }
}
