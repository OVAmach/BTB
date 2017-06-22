package com.iteamcn.BTB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Special extends BukkitRunnable{

	@Override
	public void run() {
		// TODO 自动生成的方法存根
        final Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);
        Player[] array;
        for (int length = (array = players).length, i = 0; i < length; ++i) {
            final Player player = array[i];
            final ItemStack[] armour = player.getInventory().getArmorContents();
            ItemStack[] array2;
            array2=Arrays.copyOf(armour, armour.length+1);
            final ItemStack part = player.getItemInHand();
            array2[array2.length-1]=part;
            List<PotionEffect> list = getSpecialList(array2);
            if(list!=null&&list.size()>=1){
            	//Bukkit.broadcastMessage("duang"+player.getDisplayName()+duangID);
            	player.addPotionEffects(list);
            }
        }
	}
	public List<PotionEffect> getSpecialList(ItemStack[] eq){
		List<PotionEffect> list = new ArrayList<PotionEffect>();
    	for(int i=0;i<eq.length;i++){
    		if(eq[i]!=null){
    			if(BTB.getEquip_Map().containsKey(eq[i].getType())){
    				int lvl=Intensify.getLvl(eq[i]);
    				int[] lvls=BTB.getEquip_Map().get(eq[i].getType()).getSpecial_Lvl();
    				int p=-1;
    				for(int j=0;j<lvls.length;j++){
    					if(lvl==lvls[j]){
    						p=j;
    						break;
    					}
    				}
    				if(p!=-1){
    					String[] special=BTB.getEquip_Map().get(eq[i].getType()).getSpecial_Type()[p].split(",");
        				for(int j=0;j<special.length;j++){
        					String[] t = special[j].split(":");
        					list.add(new PotionEffect(PotionEffectType.getByName(t[0]), 140, Integer.parseInt(t[1])-1, false, false));
        				}
    				}
    			}
    			
    		}
    	}
		return list;
	}

}
