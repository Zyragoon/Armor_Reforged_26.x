package com.zyra.armor_reforged.item;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ArmorSetBonuses {

    private static final List<ArmorSetBonus> ARMOR_SETS = new ArrayList<>();

    public static void registerSet(ArmorSetBonus armorSet) {
        ARMOR_SETS.add(armorSet);
    }

    public static void register() {
        ServerTickEvents.END_LEVEL_TICK.register(level -> {
            for (Player player : level.players()) {
                checkArmorSets(player);
            }
        });
    }

    private static void checkArmorSets(Player player) {

        for (ArmorSetBonus armorSet : ARMOR_SETS) {

            int pieces = 0;

            if (player.getItemBySlot(EquipmentSlot.HEAD).is(armorSet.helmet())) {
                pieces++;
            }

            if (player.getItemBySlot(EquipmentSlot.CHEST).is(armorSet.chestplate())) {
                pieces++;
            }

            if (player.getItemBySlot(EquipmentSlot.LEGS).is(armorSet.leggings())) {
                pieces++;
            }

            if (player.getItemBySlot(EquipmentSlot.FEET).is(armorSet.boots())) {
                pieces++;
            }

            for (ArmorSetBonus.EffectBonus bonus : armorSet.effects()) {

                if (pieces >= bonus.requiredPieces()) {

                    player.addEffect(new MobEffectInstance(
                            bonus.effect(),
                            2,
                            bonus.amplifier(),
                            false,
                            false,
                            false
                    ));

                }
            }
        }
    }
}