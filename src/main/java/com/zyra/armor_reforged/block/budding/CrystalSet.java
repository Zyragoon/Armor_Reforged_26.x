
package com.zyra.armor_reforged.block.budding;

import com.zyra.armor_reforged.block.crystal.CrystalBlock;
import net.minecraft.world.level.block.Block;

public record CrystalSet(
        CrystalBlock small,
        CrystalBlock medium,
        CrystalBlock large,
        CrystalBlock cluster
) {

    public CrystalSet {
        if (small.getGrowthStage() != 0 ||
                medium.getGrowthStage() != 1 ||
                large.getGrowthStage() != 2 ||
                cluster.getGrowthStage() != 3) {

            throw new IllegalArgumentException(
                    "CrystalSet blocks must have growth stages 0, 1, 2, and 3."
            );
        }
    }

    public CrystalBlock getFirstStage() {
        return small;
    }

    public CrystalBlock getNextStage(Block current) {

        if (current == small) return medium;
        if (current == medium) return large;
        if (current == large) return cluster;

        return null;
    }

    public boolean contains(Block block) {
        return block == small ||
                block == medium ||
                block == large ||
                block == cluster;
    }
}
