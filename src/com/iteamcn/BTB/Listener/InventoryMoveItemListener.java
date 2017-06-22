package com.iteamcn.BTB.Listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class InventoryMoveItemListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void hit(InventoryMoveItemEvent event){
		//Bukkit.broadcastMessage("Move");
	}
}
