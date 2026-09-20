/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 */
package com.rpgland;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;

public class PlayerLand {
    private final UUID ownerUUID;
    private final String worldName;
    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;
    private final int minZ;
    private final int maxZ;

    public PlayerLand(UUID ownerUUID, Location center) {
        this.ownerUUID = ownerUUID;
        this.worldName = center.getWorld().getName();
        this.minX = center.getBlockX() - 5;
        this.maxX = center.getBlockX() + 4;
        this.minZ = center.getBlockZ() - 5;
        this.maxZ = center.getBlockZ() + 4;
        this.minY = Math.max(center.getWorld().getMinHeight(), center.getBlockY() - 10);
        this.maxY = Math.min(center.getWorld().getMaxHeight(), center.getBlockY() + 10);
    }

    public PlayerLand(UUID ownerUUID, String worldName, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        this.ownerUUID = ownerUUID;
        this.worldName = worldName;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public boolean isInside(Location loc) {
        if (!loc.getWorld().getName().equals(this.worldName)) {
            return false;
        }
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        return x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY && z >= this.minZ && z <= this.maxZ;
    }

    public boolean intersects(PlayerLand other) {
        if (!this.worldName.equals(other.worldName)) {
            return false;
        }
        return this.minX <= other.maxX && this.maxX >= other.minX && this.minY <= other.maxY && this.maxY >= other.minY && this.minZ <= other.maxZ && this.maxZ >= other.minZ;
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("owner", this.ownerUUID.toString());
        map.put("world", this.worldName);
        map.put("minX", this.minX);
        map.put("maxX", this.maxX);
        map.put("minY", this.minY);
        map.put("maxY", this.maxY);
        map.put("minZ", this.minZ);
        map.put("maxZ", this.maxZ);
        return map;
    }

    public static PlayerLand deserialize(Map<String, Object> map) {
        return new PlayerLand(UUID.fromString((String)map.get("owner")), (String)map.get("world"), (Integer)map.get("minX"), (Integer)map.get("maxX"), (Integer)map.get("minY"), (Integer)map.get("maxY"), (Integer)map.get("minZ"), (Integer)map.get("maxZ"));
    }
}

