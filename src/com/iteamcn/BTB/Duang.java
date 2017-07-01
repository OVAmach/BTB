package com.iteamcn.BTB;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Duang extends BukkitRunnable {
//	private int duangID;
//	private Thread t;
//	private boolean stop=false;
//	public Duang(int duangID){
//		this.duangID=duangID;
//	}
//	public void playDuang(Player player){
//		if(t==null){
//			t = new Thread() {
//				
//				@Override
//				public void run() {
//					// TODO 自动生成的方法存根
//					while(true){
//						if(stop)return;
//						for(int i=0;i<5;i++){
//							if(duangID==1)player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, null, 20);
//						}
//						try {
//							Thread.sleep(500);
//						} catch (InterruptedException e) {
//							// TODO 自动生成的 catch 块
//							e.printStackTrace();
//						}
//					}
//				}
//			};
//			t.start();
//		}
//	}
//	public void stop(){
//		this.stop=true;
//	}
//	public void release(){
//		this.stop=true;
//		this.duangID=0;
//	}
//	public static void checkDuang(Player player){
//		int duangID = Intensify.getDuang(player.getEquipment().getArmorContents());
//		String name = player.getName();
//		if(duangID!=-1){
//			if(BTB.getDuangList().containsKey(name)){
//				BTB.getDuangList().get(name).release();
//				BTB.getDuangList().replace(name, new Duang(duangID));
//				BTB.getDuangList().get(name).playDuang(player);
//			}else{
//				Duang d = new Duang(duangID);
//				d.playDuang(player);
//				BTB.getDuangList().put(name, d);
//			}
//		}else{
//			if(BTB.getDuangList().containsKey(name)){
//				BTB.getDuangList().get(name).release();
//				BTB.getDuangList().remove(name);
//			}
//		}
//	}
	@Override
	public void run() {
		if(!(boolean)BTB.getIntensifySettings().get("duangMode"))return;
        final Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);
        Player[] array;
        for (int length = (array = players).length, i = 0; i < length; ++i) {
            final Player player = array[i];
            final ItemStack[] armour = player.getInventory().getArmorContents();
            ItemStack[] array2;
            array2=Arrays.copyOf(armour, armour.length+1);
            final ItemStack part = player.getItemInHand();
            array2[array2.length-1]=part;
            Collection<Integer> t = getDuang(array2);
            Integer[] duangID = t.toArray(new Integer[t.size()]);
            if(duangID.length!=0){
            	//Bukkit.broadcastMessage("duang"+player.getDisplayName()+duangID);
            	showDuang(player,duangID);
            }
        }
	}
	public static void showDuang(Player player,Integer[] duangID){
		for(int i=0;i<duangID.length;i++){
			Location t = player.getLocation();
			if(duangID[i]==1)player.getWorld().playEffect(t, Effect.ENDER_SIGNAL, null, 20);
			if(duangID[i]==2)player.getWorld().playEffect(t, Effect.MOBSPAWNER_FLAMES, null, 20);
			//Bukkit.broadcastMessage(t.toString());
			if(i==(int)BTB.getIntensifySettings().get("userMaxDuang")-1)break;
		}

	}
    public static Collection<Integer> getDuang(ItemStack[] eq){
    	if(eq==null) return null;
    	Set<Integer> list = new HashSet<Integer>();
    	for(int i=0;i<eq.length;i++){
    		if(eq[i]!=null){
    			if(BTB.getEquip_Map().containsKey(eq[i].getType())){
    				if(Intensify.getLvl(eq[i])>=BTB.getEquip_Map().get(eq[i].getType()).getDuang_Lvl()){
    					list.add(BTB.getEquip_Map().get(eq[i].getType()).getDuang_Type());
    				}
    			}
    			
    		}
    	}
    	return list;
    }
}
