/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 */
package com.rpgland;

import com.rpgland.PlayerLand;
import com.rpgland.RPGLandSystem;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class LandEventListener
implements Listener {
    private final RPGLandSystem plugin;
    private final Map<UUID, PlayerLand> playerLands = new HashMap<UUID, PlayerLand>();
    private final File dataFile;
    private FileConfiguration dataConfig;

    public LandEventListener(RPGLandSystem plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "lands.yml");
        this.loadLands();
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockBreak(BlockBreakEvent event) {
        Location loc;
        Player player = event.getPlayer();
        if (!this.canInteract(player, loc = event.getBlock().getLocation())) {
            event.setCancelled(true);
            player.sendMessage("\u00a7c[\u9818\u5730\u7cfb\u7d71] \u4f60\u6c92\u6709\u6b0a\u9650\u7834\u58de\u6b64\u8655\uff08\u79c1\u4eba\u9818\u5730\uff09\uff01");
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Location loc;
        Player player = event.getPlayer();
        if (!this.canInteract(player, loc = event.getBlock().getLocation())) {
            event.setCancelled(true);
            player.sendMessage("\u00a7c[\u9818\u5730\u7cfb\u7d71] \u4f60\u6c92\u6709\u6b0a\u9650\u5728\u6b64\u8655\u653e\u7f6e\u65b9\u584a\uff08\u79c1\u4eba\u9818\u5730\uff09\uff01");
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onContainerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }
        Player player = event.getPlayer();
        Location loc = event.getClickedBlock().getLocation();
        if (event.getClickedBlock().getType() == Material.BARREL) {
            PlayerLand landAtLoc = this.getLandAt(loc);
            if (landAtLoc != null && !landAtLoc.getOwnerUUID().equals(player.getUniqueId())) {
                event.setCancelled(true);
                player.sendMessage("\u00a7c[\u9818\u5730\u7cfb\u7d71] \u9019\u4e0d\u662f\u4f60\u7684\u9818\u5730\u6728\u6876\uff01");
                return;
            }
        } else if (!this.canInteract(player, loc)) {
            event.setCancelled(true);
            player.sendMessage("\u00a7c[\u9818\u5730\u7cfb\u7d71] \u4f60\u6c92\u6709\u6b0a\u9650\u958b\u555f\u6b64\u8655\u7684\u5bb9\u5668\uff01");
        }
    }

    @EventHandler
    public void onGUIClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (title.equals("\u00a72\ud83d\udee2\ufe0f \u500b\u4eba 10x10 \u9818\u5730\u63a7\u5236\u53f0")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                return;
            }
            Player player = (Player)event.getWhoClicked();
            if (event.getRawSlot() == 13 && event.getCurrentItem().getType() == Material.BARREL) {
                UUID pUUID = player.getUniqueId();
                if (this.playerLands.containsKey(pUUID)) {
                    player.sendMessage("\u00a7c[\u9818\u5730\u7cfb\u7d71] \u4f60\u5df2\u7d93\u64c1\u6709\u4e00\u500b\u5c08\u5c6c\u9818\u5730\u4e86\uff0c\u7121\u6cd5\u91cd\u8907\u5efa\u7acb\uff01");
                    player.closeInventory();
                    return;
                }
                PlayerLand newLand = new PlayerLand(pUUID, player.getLocation());
                for (PlayerLand existingLand : this.playerLands.values()) {
                    if (!existingLand.intersects(newLand)) continue;
                    player.sendMessage("\u00a7c[\u9818\u5730\u7cfb\u7d71] \u5708\u7acb\u5931\u6557\uff01\u6b64\u8655\u8207\u5176\u4ed6\u73a9\u5bb6\u7684\u9818\u5730\u7bc4\u570d\u91cd\u758a\u3002");
                    player.closeInventory();
                    return;
                }
                this.playerLands.put(pUUID, newLand);
                this.saveLands();
                player.sendMessage("\u00a7a[\u9818\u5730\u7cfb\u7d71] \u6210\u529f\u5708\u7acb 10x10 \u500b\u4eba\u5c08\u5c6c\u9818\u5730\uff01");
            }
            player.closeInventory();
        }
    }

    private boolean canInteract(Player player, Location loc) {
        PlayerLand land = this.getLandAt(loc);
        if (land == null) {
            return true;
        }
        return land.getOwnerUUID().equals(player.getUniqueId());
    }

    private PlayerLand getLandAt(Location loc) {
        for (PlayerLand land : this.playerLands.values()) {
            if (!land.isInside(loc)) continue;
            return land;
        }
        return null;
    }

    public void saveLands() {
        this.dataConfig = new YamlConfiguration();
        for (Map.Entry<UUID, PlayerLand> entry : this.playerLands.entrySet()) {
            this.dataConfig.set("lands." + entry.getKey().toString(), entry.getValue().serialize());
        }
        try {
            this.dataConfig.save(this.dataFile);
        }
        catch (IOException e) {
            this.plugin.getLogger().severe("\u7121\u6cd5\u5132\u5b58\u9818\u5730\u8cc7\u6599\u81f3 lands.yml!");
        }
    }

    public void loadLands() {
        if (!this.dataFile.exists()) {
            return;
        }
        this.dataConfig = YamlConfiguration.loadConfiguration((File)this.dataFile);
        if (!this.dataConfig.contains("lands")) {
            return;
        }
        for (String key : this.dataConfig.getConfigurationSection("lands").getKeys(false)) {
            UUID owner = UUID.fromString(key);
            Map map = this.dataConfig.getConfigurationSection("lands." + key).getValues(false);
            this.playerLands.put(owner, PlayerLand.deserialize(map));
        }
    }
}

