package com.iteamcn.BTB;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class Intensify{
	public static void open(Player player){
		ItemStack fence=new ItemStack(Material.IRON_FENCE,1);
		ItemMeta fencemeta = fence.getItemMeta();
		fencemeta.setDisplayName("��7Locked");
		fence.setItemMeta(fencemeta);
		Inventory inv = Bukkit.createInventory(player, 9, "ǿ�����");
		inv.setItem(1, fence);
		inv.setItem(7, fence);
		inv.setItem(8, new ItemStack(Material.ANVIL,1));
		player.closeInventory();
		player.openInventory(inv);
	}
	public static void update(Inventory inv){
		ItemStack eq = inv.getItem(0);
		ItemStack[] mat=new ItemStack[5];
		InvSet invSet=new InvSet(eq, mat);
		List<String> lore=new ArrayList<String>();
		int check=Intensify.check(invSet);
		if(check!=0){
			lore=Intensify.getCheckCodeString(check);
		}else{
			double chance = Intensify.getChance(invSet);
			lore.add("��e�ɹ�����:"+chance*100+"%");
		}
		ItemStack s8 = new ItemStack(Material.ANVIL,1);
		ItemMeta s8Meta = s8.getItemMeta();
		s8Meta.setLore(lore);
		s8Meta.setDisplayName("��bǿ��");
		s8.setItemMeta(s8Meta);
		inv.setItem(8, s8);
	}
	public static List<String> getCheckCodeString(int checkCode){
		List<String> s=new ArrayList<String>();
		for(int i=9;i>=0;i--){
			if((checkCode/(int)Math.pow(2, i))==1){
				checkCode-=(int)Math.pow(2, i);
				switch(i){
					case 9:
						s.add("ǿ��ʯ�������ԣ����ֻ�ܷ�"+BTB.getIntensifySettings().get("maxMaterials")+"��ǿ��ʯ");
						break;
					case 8:
						s.add("װ��������");
						break;
					case 7:
						s.add("5�Ų۲���ǿ��ʯ");
						break;
					case 6:
						s.add("4�Ų۲���ǿ��ʯ");
						break;
					case 5:
						s.add("3�Ų۲���ǿ��ʯ");
						break;
					case 4:
						s.add("2�Ų۲���ǿ��ʯ");
						break;
					case 3:
						s.add("1�Ų۲���ǿ��ʯ");
						break;
					case 2:
						s.add("û��ǿ��ʯ");
						break;
					case 1:
						s.add("װ������ǿ��");
						break;
					case 0:
						s.add("û��װ��");
						break;
				}
			}
		}
		return s;
		
	}
	public static double getChance(InvSet inv){
		
		if(check(inv)==0){
			double s=0;
			ItemStack[] mat = inv.getMat();
			int lvl=getLvl(inv.getEq())+1;
			Map<Material, BTB_Material> Material_Map = BTB.getMaterial_Map();
			for(int i=0;i<5;i++){
				if(mat[i]!=null){
					s+=Material_Map.get(mat[i].getType()).getChance(lvl)/(double)((int)BTB.getIntensifySettings().get("maxMaterials"))*(double)mat[i].getAmount()/100.0;
				}
			}
			return s;
		}else{
			return -1;
		}
	}
	public static int getLvl(ItemStack eq){
		int lvl=0;
		if(eq!=null){
			if(eq.getItemMeta()!=null){
				ItemMeta eqMeta = eq.getItemMeta();
				if(eqMeta.hasLore()){
					List<String> eqLore = eqMeta.getLore();
					for(int i = 0;i<=eqLore.size()-1;i++){
						if(eqLore.get(i).startsWith("��eǿ���ȼ�:")){
							try{
								lvl=Integer.parseInt(eqLore.get(i).substring(7));
							}catch (NumberFormatException e) {
								System.out.println(e);
							}
						}
					}
				}else{
					
				}
			}
		}
		return lvl;
	}
	public static int check(InvSet inv){
		int s=0;
		ItemStack eq = inv.getEq();
		if(eq==null){
			s+=1; //1ûװ��
		}else{
			if((!BTB.getEquip_Map().containsKey(eq.getType()))||eq.getAmount()!=1) s+=2;//2װ������ǿ��
		}
		int p = 0,t=0;
		for(int i=1;i<=5;i++){
			if(inv.getMat()[i-1]==null)continue;
			t+=inv.getMat()[i-1].getAmount();
			if(!BTB.getMaterial_Map().containsKey(inv.getMat()[i-1].getType())) s+=Math.pow(2, i+2);else p++;//8,16,32,64,128���¼����۲���ǿ����
		}
		if(t>(int)BTB.getIntensifySettings().get("maxMaterials")){
			s+=512;
		}
		if(p==0)s+=4;//4ûǿ����
		if(getLvl(eq)>=(int)BTB.getIntensifySettings().get("maxLevel")) s+=256;//256������
		
		return s;
	}
	private static double random(ItemStack eq,int lvl){
		double max = BTB.getEquip_Map().get(eq.getType()).getProperty_Range_Max();
		double min = BTB.getEquip_Map().get(eq.getType()).getProperty_Range_Min();
		double ran = ((double)((int)(Math.random()*(((int)(max*10))-((int)(min*10))+1)+((int)(min*10)))))/10;
		DecimalFormat df = new DecimalFormat("0.0");
		ran=Double.valueOf(df.format(ran));
		return ran;
	}
	private static ItemStack successInt(ItemStack eq){
		int lvl = getLvl(eq);
		if(lvl==0){
			lvl=1;
			ItemMeta eqMeta = eq.getItemMeta();
			if(eqMeta.hasLore()){
				List<String> lore = eqMeta.getLore();
				lore.add("��eǿ���ȼ�:1");
				
				StringBuilder  build=new StringBuilder("");
				for(int i = 1;i<=(int)BTB.getIntensifySettings().get("maxLevel");i++){
					if(i<=lvl){
						build.append('��');
					}else{
						build.append('��');
					}
				}
				double inf = random(eq,lvl);
				lore.add("��d"+build.toString()+"��a"+inf);
				lore.add("��61��ǿ��:"+"��a+"+inf);
				eqMeta.setLore(lore);
			}else{
				List<String> lore=new ArrayList<String>();
				lore.add("��eǿ���ȼ�:1");
				
				StringBuilder  build=new StringBuilder("");
				for(int i = 1;i<=(int)BTB.getIntensifySettings().get("maxLevel");i++){
					if(i<=lvl){
						build.append('��');
					}else{
						build.append('��');
					}
				}
				double inf = random(eq,lvl);
				lore.add("��d"+build.toString()+"��a"+inf);
				lore.add("��61��ǿ��:"+"��a+"+inf);
				eqMeta.setLore(lore);
			}
			eq.setItemMeta(eqMeta);
			updataNBT(eq);
			return eq;
		}else{
			ItemMeta eqMeta = eq.getItemMeta();
			List<String> lore = eqMeta.getLore();
			lvl++;
			String f="��{1}d{1}��+��*";
			int t=0;
			for(int i=0;i<=lore.size()-1;i++){
				if(lore.get(i).startsWith("��eǿ���ȼ�:")){
					lore.set(i, "��eǿ���ȼ�:"+lvl);
				}else if(lore.get(i).startsWith("��d��")){
					if(lore.get(i).substring(0, 2+(int)BTB.getIntensifySettings().get("maxLevel")).matches(f)){
						t=i;
					}
				}
			}
			StringBuilder  build=new StringBuilder("");
			for(int i = 1;i<=(int)BTB.getIntensifySettings().get("maxLevel");i++){
				if(i<=lvl){
					build.append('��');
				}else{
					build.append('��');
				}
			}
			double p = getProperty(eq);
			double inf = random(eq,lvl);
			lore.set(t,"��d"+build.toString()+"��a"+(inf*100+p*100)/100 );
			lore.add("��6"+lvl+"��ǿ��:"+"��a+"+inf);
			eqMeta.setLore(lore);
			eq.setItemMeta(eqMeta);
			updataNBT(eq);
			return eq;
		}
	}
	public static void updataNBT(ItemStack eq){
		if(eq==null)return;
		NBTCompound itemData = BTB.getNBTManager().read(eq);
		if(Intensify.getPropertyType(eq).equalsIgnoreCase("attack")){
			double attack=BTB.getEquip_Map().get(eq.getType()).getProperty_Base();
			int flag=0;
			if(itemData.containsKey("AttributeModifiers")){
				NBTList list = itemData.getList("AttributeModifiers");
				for(int i=0;i<list.size();i++){
					NBTCompound temp = (NBTCompound) list.get(i);
					if(temp.getString("Name").equals("Attack")){
						temp.put("Amount", attack+getProperty(eq));
						flag=1;
					}
				}
				if(flag==0){
					NBTCompound temp=new NBTCompound();
					temp.put("AttributeName", "generic.attackDamage");
					temp.put("Name", "Attack");
					temp.put("Operation", 0);
					temp.put("Amount", attack+getProperty(eq));
					temp.put("UUIDMost", 1l);
					temp.put("UUIDLeast", 1l);
					list.add(temp);
				}
				
			}else{
				NBTList list=new NBTList();
				NBTCompound temp=new NBTCompound();
				temp.put("AttributeName", "generic.attackDamage");
				temp.put("Name", "Attack");
				temp.put("Operation", 0);
				temp.put("Amount", attack+getProperty(eq));
				temp.put("UUIDMost", 1l);
				temp.put("UUIDLeast", 1l);
				list.add(temp);
				itemData.put("AttributeModifiers", list);
			}
		}
//		if(itemData.containsKey("CustomPotionEffects")){
//			itemData.remove("CustomPotionEffects");
//		}
//		int[] a = BTB.getEquip_Map().get(eq.getType()).getSpecial_Lvl();
//		
//		int lvl=Intensify.getLvl(eq);
//		if(a.length>0){
//			//Bukkit.broadcastMessage("->290");
//			int p=-1;
//			for(int i=0;i<a.length;i++){
//				if(a[i]==lvl){
//					p=i;
//					break;
//				}
//			}
//			if(p!=-1){
//				//Bukkit.broadcastMessage("->299");
//				String[] b = BTB.getEquip_Map().get(eq.getType()).getSpecial_Type()[p].split(",");
//				Bukkit.broadcastMessage(Arrays.toString(b));
//				NBTList list=new NBTList();
//				for(int i=0;i<b.length;i++){
//					NBTCompound temp=new NBTCompound();
//					String[] t = b[i].split(":");
//					temp.put("Id", Integer.parseInt(t[0]));
//					temp.put("Amplifier", Integer.parseInt(t[1]));
//					temp.put("Duration", Integer.MAX_VALUE);
//					temp.put("ShowParticles", 0);
//					list.add(temp);
//				}
//				itemData.put("CustomPotionEffects", list);
//			}
//		}
		BTB.getNBTManager().write(eq, itemData);
	}
	public static double getProperty(ItemStack eq){
		double p=0;
		if(eq!=null){
			if(eq.getItemMeta()!=null){
				ItemMeta eqMeta = eq.getItemMeta();

				if(eqMeta.hasLore()){
					List<String> lore = eqMeta.getLore();
					String f="��{1}d{1}��+��*";

					for(int i=0;i<=lore.size()-1;i++){
						if(lore.get(i).startsWith("��d��")){
							if(lore.get(i).substring(0, 2+(int)BTB.getIntensifySettings().get("maxLevel")).matches(f)){
								p=Double.valueOf(lore.get(i).substring(4+(int)BTB.getIntensifySettings().get("maxLevel")));
							}
							
						}
					}
					DecimalFormat df = new DecimalFormat("0.0");
					p=Double.valueOf(df.format(p));
				}

			}
		}
		return p;
	}
	private static ItemStack failInt(ItemStack eq){
		int lvl = getLvl(eq);
		if(lvl==0){
			if(((String)BTB.getLevel().get(lvl).get("intensify_Fail")).equalsIgnoreCase("break")){
				eq=null;
			}
			return eq;
		}else{
			ItemMeta eqMeta = eq.getItemMeta();
			List<String> lore = eqMeta.getLore();
			double p = getProperty(eq);
			if(((String)BTB.getLevel().get(lvl).get("intensify_Fail")).equalsIgnoreCase("break")){
				if((Boolean)BTB.getIntensifySettings().get("breakPriceEnable")){
					ItemStack breakItem = BTB.getBreakPrice();
					eq.setType(breakItem.getType());
					eq.setItemMeta(breakItem.getItemMeta());
					eq.setDurability((short) 0);
					return eq;
				}else{
					eq=null;
					return eq;
				}
			}else if(((String)BTB.getLevel().get(lvl).get("intensify_Fail")).equalsIgnoreCase("down")){
				lvl--;
				String f="��{1}d{1}��+��*";
				int t=0;
				double inf=0;
				for(int i=lore.size()-1;i>=0;i--){
					if(lore.get(i).startsWith("��eǿ���ȼ�:")){
						lore.set(i, "��eǿ���ȼ�:"+(lvl));
					}else if(lore.get(i).startsWith("��d��")){
						if(lore.get(i).substring(0, 2+(int)BTB.getIntensifySettings().get("maxLevel")).matches(f)){
							t=i;
						}
					}else if(lore.get(i).startsWith("��6"+(lvl+1)+"��ǿ��:")){
						inf=Double.valueOf(lore.get(i).substring(2+String.valueOf(lvl).length()+7, lore.get(i).length()));
						lore.remove(i);
					}
				}
				StringBuilder  build=new StringBuilder("");
				for(int i = 1;i<=(int)BTB.getIntensifySettings().get("maxLevel");i++){
					if(i<=lvl){
						build.append('��');
					}else{
						build.append('��');
					}
				}
				lore.set(t, "��d"+build.toString()+"��a"+(p*100-inf*100)/100);
			}else{
				
			}
			eqMeta.setLore(lore);
			eq.setItemMeta(eqMeta);
			updataNBT(eq);
			return eq;
		}
	}
    public static String getPropertyType(ItemStack eq){
    	if(eq!=null){
    		if(BTB.getEquip_Map().get(eq.getType())!=null){
    			return BTB.getEquip_Map().get(eq.getType()).getProperty_Type();
    		}
    	}
    	return "Error";
    }
	public static void addDmgOnProjectile(Projectile projectile,double dmg){
		if(projectile!=null){
			setMetadata(projectile,"BTB_DMG", dmg,BTB.getBtb());
		}
	}
    public static void setMetadata(Entity entity, String key, Object value, Plugin plugin){
        entity.setMetadata(key, new FixedMetadataValue(plugin, value));
    }

	public static void ItemIntensify(HumanEntity human,Inventory inv){
		ItemStack eq = inv.getItem(0);
		ItemStack[] mat=new ItemStack[5];
		for(int i=0;i<5;i++){
			mat[i]=inv.getItem(2+i);
		}
		InvSet invSet=new InvSet(eq, mat);
		int check = check(invSet);
		if(BTB.getBreakPrice().equals(eq)){
			((Player)human).getWorld().playSound(human.getLocation(), Sound.LEVEL_UP, 1f, 5f);
			human.sendMessage("��b[ǿ��]��6��ϲ���"+human.getName()+"��r��6�ɹ�����4[��ѧ!!AV��ɱ������]��r��6ǿ������e+12");
			return;
		}
		if(check!=0) return;
		int lvl = getLvl(eq);
		double chance = getChance(invSet);
		double temp = Math.random()*100;
		if(temp<=chance*100){
			successInt(eq);
			if(human instanceof Player){
				((Player)human).getWorld().playSound(human.getLocation(), Sound.ANVIL_USE, 1f, 5f);
			}
			for(int i=2;i<=6;i++){
				inv.setItem(i, null);
			}
			if((Boolean)BTB.getLevel().get(lvl).get("broadcast")){
				if(eq.getItemMeta().hasDisplayName()){
					Bukkit.broadcastMessage("��b[ǿ��]��6��ϲ���"+human.getName()+"��r��6�ɹ�����4"+eq.getItemMeta().getDisplayName()+"��r��6ǿ������e+"+(lvl+1));
				}else{
					Bukkit.broadcastMessage("��b[ǿ��]��6��ϲ���"+human.getName()+"��r��6�ɹ�����4"+eq.getType().name()+"��r��6ǿ������e+"+(lvl+1));
				}
			}
			lvl=getLvl(eq);
			if(getProperty(eq)==BTB.getEquip_Map().get(eq.getType()).getProperty_Range_Max()*lvl){
				if(lvl>1){
					if(eq.getItemMeta().hasDisplayName()){
						Bukkit.broadcastMessage("��b[ǿ��]��6�ۣ�"+human.getName()+"��r��6��ǿ����4"+eq.getItemMeta().getDisplayName()+"��r��6ʱ��������e+"+(lvl)+"��r��6����������������");
					}else{
						Bukkit.broadcastMessage("��b[ǿ��]��6�ۣ�"+human.getName()+"��r��6��ǿ����4"+eq.getType().name()+"��r��6ʱ��������e+"+(lvl)+"��r��6����������������");
					}
				}
			}
		}else{
			failInt(eq);
			if(human instanceof Player){
				((Player)human).getWorld().playSound(human.getLocation(), Sound.ANVIL_BREAK, 1f, 5f);
			}
			for(int i=2;i<=6;i++){
				inv.setItem(i, null);
			}
			if((Boolean)BTB.getLevel().get(lvl).get("broadcast")){
				if(eq.getItemMeta().hasDisplayName()){
					Bukkit.broadcastMessage("��b[ǿ��]��6���"+human.getName()+"��r��6�ڳ��Խ���4"+eq.getItemMeta().getDisplayName()+"��r��6ǿ������e+"+(lvl+1)+"��6��ʱ��ʧ����");
				}else{
					Bukkit.broadcastMessage("��b[ǿ��]��6���"+human.getName()+"��r��6�ڳ��Խ���4"+eq.getType().name()+"��r��6ǿ������e+"+(lvl+1)+"��6��ʱ��ʧ����");
				}
			}
		}
		//human.sendMessage(String.valueOf(chance));
	}
	//human.sendMessage("Intensify");
}
