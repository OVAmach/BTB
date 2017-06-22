package com.iteamcn.BTB;

import org.bukkit.inventory.ItemStack;

public class InvSet {
	private ItemStack eq;
	private ItemStack[] mat;
	public InvSet(ItemStack eq,ItemStack[] mat){
		this.eq=eq;
		this.mat=mat;
	}
	public ItemStack getEq() {
		return eq;
	}
	public void setEq(ItemStack eq) {
		this.eq = eq;
	}
	public ItemStack[] getMat() {
		return mat;
	}
	public void setMat(ItemStack[] mat) {
		this.mat = mat;
	}
	
}
