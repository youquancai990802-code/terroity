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

public class BarrelGUI {
    public static void openBarrelMenu(Player player, boolean isOwner) {
        ItemStack claimItem;
        ItemMeta claimMeta;
        Inventory gui = Bukkit.createInventory(null, (int)27, (String)"\u00a72\ud83d\udee2\ufe0f \u500b\u4eba 10x10 \u9818\u5730\u63a7\u5236\u53f0");
        ItemStack infoItem = new ItemStack(Material.OAK_SIGN);
        ItemMeta infoMeta = infoItem.getItemMeta();
        if (infoMeta != null) {
            infoMeta.setDisplayName("\u00a7a\u9818\u5730\u6b0a\u9650\u72c0\u614b");
            infoMeta.setLore(Collections.singletonList(isOwner ? "\u00a77\u4f60\u662f\u6b64 10x10 \u9818\u5730\u7684\u4e3b\u4eba" : "\u00a7c\u4f60\u53ea\u6709\u8a2a\u5ba2\u6b0a\u9650"));
            infoItem.setItemMeta(infoMeta);
        }
        if ((claimMeta = (claimItem = new ItemStack(Material.BARREL)).getItemMeta()) != null) {
            claimMeta.setDisplayName("\u00a7e\u5708\u7acb\u6b64\u5730 (10x10)");
            claimMeta.setLore(Collections.singletonList("\u00a77\u5728\u6b64\u4f4d\u7f6e\u5efa\u7acb\u4f60\u7684\u5c08\u5c6c 10x10 \u500b\u4eba\u9818\u5730"));
            claimItem.setItemMeta(claimMeta);
        }
        gui.setItem(11, infoItem);
        gui.setItem(15, claimItem);
        player.openInventory(gui);
    }
}

