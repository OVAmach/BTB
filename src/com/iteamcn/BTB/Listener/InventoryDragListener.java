package com.iteamcn.BTB.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

import com.iteamcn.BTB.Intensify;

public class InventoryDragListener implements Listener{
	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void onDrag(InventoryDragEvent event){
		if(event.getInventory().getTitle().equalsIgnoreCase("Ç¿»¯Ãæ°å")){
			event.setCancelled(true);
			Intensify.update(event.getInventory());
		}
	}
}