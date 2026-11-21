package com.zyra.armor_reforged.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

import java.util.List;

public record ArmorSetBonus(
        Item helmet,
        Item chestplate,
        Item leggings,
        Item boots,
        List<EffectBonus> effects
) {

    public record EffectBonus(
            int requiredPieces,
            Holder<MobEffect> effect,
            int amplifier
    ) {}
}