package com.zyra.armor_reforged;

import com.zyra.armor_reforged.block.ModBlocks;
import com.zyra.armor_reforged.block.entity.ModBlockEntities;
import com.zyra.armor_reforged.creativemodetab.ModCreativeModeTabs;
import com.zyra.armor_reforged.item.ModItems;

import com.zyra.armor_reforged.menu.ModMenuTypes;

import net.fabricmc.api.ModInitializer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmorReforged implements ModInitializer {
	public static final String MOD_ID = "armor_reforged";



	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModCreativeModeTabs.registerModCreativeModeTabs();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.initialize();
		ModMenuTypes.initialize();



	}
}