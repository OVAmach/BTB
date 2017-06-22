package com.iteamcn.BTB.Listener;

import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.iteamcn.BTB.Intensify;

public class EntityShootBowListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void shootBow(EntityShootBowEvent event){
		double dmg = Intensify.getProperty(event.getBow());
		if(Intensify.getPropertyType(event.getBow()).equalsIgnoreCase("shoot")){
			Intensify.addDmgOnProjectile((Projectile)event.getProjectile(), dmg);
		}
		
	}
}
