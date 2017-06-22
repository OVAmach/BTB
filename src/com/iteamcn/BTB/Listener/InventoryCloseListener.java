package com.iteamcn.BTB.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.iteamcn.BTB.Duang;


public class InventoryCloseListener  implements Listener{
	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void close(InventoryCloseEvent event){
		if(event.getPlayer() instanceof Player){
			Inventory inv = event.getInventory();
			if(inv.getTitle().equalsIgnoreCase("Ç¿»¯Ãæ°å")){
				HumanEntity player = event.getPlayer();
				if(inv.getItem(0)!=null){
					player.getWorld().dropItem(player.getLocation(), inv.getItem(0));
				}
				for(int i = 2;i<=6;i++){
					if(inv.getItem(i)!=null){
						player.getWorld().dropItem(player.getLocation(), inv.getItem(i));
					}
				}
			}
			//Bukkit.broadcastMessage(inv.getType().toString());
//			if(inv.getType().equals(InventoryType.CRAFTING)){
//				Duang.checkDuang((Player)event.getPlayer());
//				//Bukkit.broadcastMessage("Duang");
//			}
		}

	}
}
