package com.zyra.armor_reforged.item;

import net.minecraft.world.effect.MobEffects;

import java.util.List;

public class ModArmorSets {

    public static void registerSets() {

        // Oceanic Armor Set
        ArmorSetBonuses.registerSet(new ArmorSetBonus(
                ModItems.OCEANIC_HELMET,
                ModItems.OCEANIC_CHESTPLATE,
                ModItems.OCEANIC_LEGGINGS,
                ModItems.OCEANIC_BOOTS,

                List.of(
                        new ArmorSetBonus.EffectBonus(
                                2,
                                MobEffects.CONDUIT_POWER,
                                0
                        ),

                        new ArmorSetBonus.EffectBonus(
                                3,
                                MobEffects.CONDUIT_POWER,
                                1
                        ),

                        new ArmorSetBonus.EffectBonus(
                                4,
                                MobEffects.CONDUIT_POWER,
                                2
                        ),

                        new ArmorSetBonus.EffectBonus(
                                4,
                                MobEffects.DOLPHINS_GRACE,
                                0
                        )
                )
        ));

    }
}