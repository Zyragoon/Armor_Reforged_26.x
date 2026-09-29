package com.zyra.armor_reforged.item;

import com.zyra.armor_reforged.ArmorReforged;
import com.zyra.armor_reforged.tags.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class ModArmorMaterials {
    public static final ResourceKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY =
            ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));
    public static final ResourceKey<EquipmentAsset> STEEL_KEY = ResourceKey.create(REGISTRY_KEY, Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, "steel"));

    public static final ArmorMaterial STEEL_ARMOR_MATERIAL = new ArmorMaterial(20,
            ArmorMaterials.makeDefense(3,6,7,3,6),
            15, SoundEvents.ARMOR_EQUIP_IRON,1,0, ModTags.Items.STEEL_REPAIR, STEEL_KEY);
}
