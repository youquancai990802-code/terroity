/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.rpgland;

import java.util.Collections;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LandGUI {
    public static void openLandMenu(Player player) {
        ItemStack payItem;
        ItemMeta payMeta;
        ItemStack buyItem;
        ItemMeta buyMeta;
        Inventory gui = Bukkit.createInventory(null, (int)27, (String)"\u00a71\ud83d\udcdc \u738b\u90fd\u5e02\u96c6\u7ba1\u7406\u9762\u677f");
        ItemStack statusItem = new ItemStack(Material.PAPER);
        ItemMeta statusMeta = statusItem.getItemMeta();
        if (statusMeta != null) {
            statusMeta.setDisplayName("\u00a7a\u67e5\u770b\u571f\u5730\u72c0\u614b");
            statusMeta.setLore(Collections.singletonList("\u00a77\u9ede\u64ca\u67e5\u770b\u7576\u524d\u5340\u57df\u7684\u7522\u6b0a\u8207\u7e73\u7a05\u8cc7\u8a0a"));
            statusItem.setItemMeta(statusMeta);
        }
        if ((buyMeta = (buyItem = new ItemStack(Material.GOLD_INGOT)).getItemMeta()) != null) {
            buyMeta.setDisplayName("\u00a7e\u8cb7\u65b7\u571f\u5730");
            buyMeta.setLore(Collections.singletonList("\u00a77\u9ede\u64ca\u8cfc\u8cb7\u7576\u524d\u6240\u5728\u7684\u571f\u5730"));
            buyItem.setItemMeta(buyMeta);
        }
        if ((payMeta = (payItem = new ItemStack(Material.EMERALD)).getItemMeta()) != null) {
            payMeta.setDisplayName("\u00a7b\u7e73\u7d0d\u571f\u5730\u7a05");
            payMeta.setLore(Collections.singletonList("\u00a77\u9ede\u64ca\u5ef6\u9577\u571f\u5730\u4f7f\u7528\u671f\u9650 30 \u5929"));
            payItem.setItemMeta(payMeta);
        }
        gui.setItem(11, statusItem);
        gui.setItem(13, buyItem);
        gui.setItem(15, payItem);
        player.openInventory(gui);
    }
}

