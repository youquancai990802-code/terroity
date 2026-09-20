/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.rpgland;

import com.rpgland.RPGLandSystem;
import java.util.UUID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LandCommand
implements CommandExecutor {
    private final RPGLandSystem plugin;

    public LandCommand(RPGLandSystem plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String subCommand;
        if (!(sender instanceof Player)) {
            sender.sendMessage("\u00a7c\u6b64\u6307\u4ee4\u53ea\u80fd\u7531\u73a9\u5bb6\u57f7\u884c\uff01");
            return true;
        }
        Player player = (Player)sender;
        UUID pUUID = player.getUniqueId();
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            player.sendMessage("\u00a7e=== RPGLand \u571f\u5730\u6307\u4ee4\u5217\u8868 ===");
            player.sendMessage("\u00a7a/land status \u00a77- \u67e5\u8a62\u571f\u5730\u8207\u7e73\u7a05\u72c0\u6cc1");
            player.sendMessage("\u00a7a/land buy \u00a77- \u8cb7\u65b7\u571f\u5730");
            player.sendMessage("\u00a7a/land pay \u00a77- \u5ef6\u9577\u7e73\u7a05\u671f\u9650");
            return true;
        }
        switch (subCommand = args[0].toLowerCase()) {
            case "status": {
                boolean isAuctioned = this.plugin.getAuctionedLands().contains(pUUID.toString());
                Long deadline = this.plugin.getTaxDeadlines().get(pUUID);
                player.sendMessage("\u00a7a[\u571f\u5730\u72c0\u614b] \u67e5\u8a62\u7d50\u679c\uff1a");
                player.sendMessage("\u00a77\u6cd5\u62cd\u72c0\u614b: " + (isAuctioned ? "\u00a7c\u6cd5\u62cd\u4e2d" : "\u00a7a\u6b63\u5e38"));
                if (deadline != null) {
                    player.sendMessage("\u00a77\u5230\u671f\u6642\u9593\u6233: \u00a7e" + deadline);
                    break;
                }
                player.sendMessage("\u00a77\u5230\u671f\u6642\u9593\u6233: \u00a77\u7121\u8a18\u9304");
                break;
            }
            case "buy": {
                player.sendMessage("\u00a7a[\u571f\u5730\u7cfb\u7d71] \u6210\u529f\u8cfc\u8cb7\u571f\u5730\uff01");
                break;
            }
            case "pay": {
                long nextDeadline = System.currentTimeMillis() + 604800000L;
                this.plugin.getTaxDeadlines().put(pUUID, nextDeadline);
                this.plugin.getAuctionedLands().remove(pUUID.toString());
                player.sendMessage("\u00a7a[\u571f\u5730\u7cfb\u7d71] \u6210\u529f\u88dc\u7e73\u7a05\u6b3e\uff0c\u671f\u9650\u5df2\u5ef6\u9577 7 \u5929\uff01");
                break;
            }
            default: {
                player.sendMessage("\u00a7c\u672a\u77e5\u7684\u5b50\u6307\u4ee4\uff0c\u8acb\u4f7f\u7528 /land help \u6aa2\u8996\u8aaa\u660e\u3002");
            }
        }
        return true;
    }
}

