
package com.zyra.armor_reforged.block.budding;

import com.zyra.armor_reforged.block.crystal.CrystalBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class BuddingCrystalBlock extends Block {

    private final CrystalSet crystalSet;

    public BuddingCrystalBlock(
            Properties properties,
            CrystalSet crystalSet
    ) {
        super(properties.randomTicks());

        this.crystalSet = crystalSet;
    }

    @Override
    protected void randomTick(
            BlockState state,
            ServerLevel level,
            BlockPos pos,
            RandomSource random
    ) {

        // 1 in 5 chance to attempt growth.
        if (random.nextInt(5) != 0) {
            return;
        }

        // Select one of the six directions.
        Direction direction = Direction.getRandom(random);

        BlockPos targetPos = pos.relative(direction);

        BlockState targetState = level.getBlockState(targetPos);

        FluidState fluidState = level.getFluidState(targetPos);

        boolean waterlogged =
                fluidState.is(Fluids.WATER) && fluidState.isSource();

        // Attempt to create a new crystal.
        if (targetState.isAir() || waterlogged) {

            growNewCrystal(
                    level,
                    targetPos,
                    direction,
                    waterlogged
            );

            return;
        }

        // Otherwise, attempt to advance an existing crystal.
        growExistingCrystal(
                level,
                pos,
                targetPos,
                direction,
                targetState
        );
    }

    private void growNewCrystal(
            ServerLevel level,
            BlockPos pos,
            Direction direction,
            boolean waterlogged
    ) {

        BlockState crystalState = crystalSet.getFirstStage()
                .defaultBlockState()
                .setValue(CrystalBlock.FACING, direction)
                .setValue(CrystalBlock.WATERLOGGED, waterlogged);

        // Make sure the crystal can attach to the budding block.
        if (!crystalState.canSurvive(level, pos)) {
            return;
        }

        level.setBlock(pos, crystalState, 3);
    }

    private void growExistingCrystal(
            ServerLevel level,
            BlockPos buddingPos,
            BlockPos crystalPos,
            Direction direction,
            BlockState currentState
    ) {

        // Only work with our custom crystal blocks.
        if (!(currentState.getBlock() instanceof CrystalBlock currentCrystal)) {
            return;
        }

        // Make sure the crystal belongs to this budding block's crystal set.
        if (!crystalSet.contains(currentCrystal)) {
            return;
        }

        // Make sure this crystal is attached to this budding block.
        if (currentState.getValue(CrystalBlock.FACING) != direction) {
            return;
        }

        if (!crystalPos.relative(direction.getOpposite()).equals(buddingPos)) {
            return;
        }

        // Get the next growth stage.
        CrystalBlock nextStage = crystalSet.getNextStage(currentCrystal);

        // No next stage means the crystal is fully grown.
        if (nextStage == null) {
            return;
        }

        // Preserve existing block state properties.
        BlockState nextState = nextStage.defaultBlockState()
                .setValue(
                        CrystalBlock.FACING,
                        currentState.getValue(CrystalBlock.FACING)
                )
                .setValue(
                        CrystalBlock.WATERLOGGED,
                        currentState.getValue(CrystalBlock.WATERLOGGED)
                );

        // Verify that the new stage can still survive.
        if (!nextState.canSurvive(level, crystalPos)) {
            return;
        }

        level.setBlock(crystalPos, nextState, 3);
    }
}
