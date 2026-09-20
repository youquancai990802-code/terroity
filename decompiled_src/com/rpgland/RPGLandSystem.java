/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.rpgland;

import com.rpgland.LandCommand;
import com.rpgland.LandEventListener;
import com.rpgland.WGHook;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGLandSystem
extends JavaPlugin {
    private static RPGLandSystem instance;
    private final Set<String> auctionedLands = new HashSet<String>();
    private final Map<UUID, Long> taxDeadlines = new HashMap<UUID, Long>();

    public void onEnable() {
        instance = this;
        WGHook.init();
        this.getServer().getPluginManager().registerEvents((Listener)new LandEventListener(this), (Plugin)this);
        if (this.getCommand("land") != null) {
            this.getCommand("land").setExecutor((CommandExecutor)new LandCommand(this));
        }
        this.getLogger().info("RPGLandSystem v4.0 \u5df2\u6210\u529f\u555f\u7528\uff01");
    }

    public void onDisable() {
        this.getLogger().info("RPGLandSystem \u5df2\u5b89\u5168\u95dc\u9589\u3002");
    }

    public static RPGLandSystem getInstance() {
        return instance;
    }

    public Set<String> getAuctionedLands() {
        return this.auctionedLands;
    }

    public Map<UUID, Long> getTaxDeadlines() {
        return this.taxDeadlines;
    }
}

