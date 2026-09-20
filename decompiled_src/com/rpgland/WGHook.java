/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.bukkit.BukkitAdapter
 *  com.sk89q.worldedit.util.Location
 *  com.sk89q.worldguard.WorldGuard
 *  com.sk89q.worldguard.protection.ApplicableRegionSet
 *  com.sk89q.worldguard.protection.regions.ProtectedRegion
 *  com.sk89q.worldguard.protection.regions.RegionContainer
 *  com.sk89q.worldguard.protection.regions.RegionQuery
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 */
package com.rpgland;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class WGHook {
    private static boolean isWGEnabled = false;

    public static void init() {
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            isWGEnabled = true;
            Bukkit.getLogger().info("[RPGLandSystem] \u5df2\u6210\u529f\u639b\u9264 WorldGuard API\uff01");
        } else {
            Bukkit.getLogger().warning("[RPGLandSystem] \u672a\u5075\u6e2c\u5230 WorldGuard\uff0c\u5340\u57df\u6aa2\u67e5\u529f\u80fd\u5c07\u7121\u6cd5\u904b\u4f5c\u3002");
        }
    }

    public static boolean isInRegion(Location loc, String regionName) {
        if (!isWGEnabled || loc == null) {
            return false;
        }
        try {
            com.sk89q.worldedit.util.Location weLoc = BukkitAdapter.adapt((Location)loc);
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(weLoc);
            for (ProtectedRegion region : set) {
                if (!region.getId().equalsIgnoreCase(regionName)) continue;
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

