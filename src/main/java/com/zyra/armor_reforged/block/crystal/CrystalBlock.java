
package com.zyra.armor_reforged.block.crystal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrystalBlock extends Block implements SimpleWaterloggedBlock {

    public static final EnumProperty<Direction> FACING =
            BlockStateProperties.FACING;

    public static final BooleanProperty WATERLOGGED =
            BlockStateProperties.WATERLOGGED;

    private final int growthStage;

    public CrystalBlock(Properties properties, int growthStage) {
        super(properties);

        if (growthStage < 0 || growthStage > 3) {
            throw new IllegalArgumentException(
                    "Crystal growth stage must be between 0 and 3."
            );
        }

        this.growthStage = growthStage;

        registerDefaultState(
                defaultBlockState()
                        .setValue(FACING, Direction.UP)
                        .setValue(WATERLOGGED, false)
        );
    }

    public int getGrowthStage() {
        return growthStage;
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(FACING, WATERLOGGED);
    }

    // Handles player placement.
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Direction direction = context.getClickedFace();

        FluidState fluidState = context.getLevel()
                .getFluidState(context.getClickedPos());

        boolean waterlogged =
                fluidState.is(Fluids.WATER) && fluidState.isSource();

        BlockState state = defaultBlockState()
                .setValue(FACING, direction)
                .setValue(WATERLOGGED, waterlogged);

        return state.canSurvive(
                context.getLevel(),
                context.getClickedPos()
        ) ? state : null;
    }

    // Checks whether the crystal has a valid supporting block.
    @Override
    protected boolean canSurvive(
            BlockState state,
            LevelReader level,
            BlockPos pos
    ) {
        Direction facing = state.getValue(FACING);

        BlockPos supportPos = pos.relative(facing.getOpposite());

        return level.getBlockState(supportPos)
                .isFaceSturdy(level, supportPos, facing);
    }

    // Handles neighboring block changes and water ticks.
    @Override
    protected BlockState updateShape(
            BlockState state,
            LevelReader level,
            ScheduledTickAccess ticks,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource random
    ) {
        BlockState updatedState = super.updateShape(
                state,
                level,
                ticks,
                pos,
                direction,
                neighborPos,
                neighborState,
                random
        );

        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(
                    pos,
                    Fluids.WATER,
                    Fluids.WATER.getTickDelay(level)
            );
        }

        if (!updatedState.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return updatedState;
    }

    // Makes Minecraft recognize the water inside the crystal.
    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED)
                ? Fluids.WATER.getSource(false)
                : super.getFluidState(state);
    }

    // Determines the crystal's shape based on its direction and stage.
    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        double length = switch (growthStage) {
            case 0 -> 4.0;
            case 1 -> 6.0;
            case 2 -> 9.0;
            default -> 12.0;
        };

        double width = switch (growthStage) {
            case 0 -> 4.0;
            case 1 -> 6.0;
            case 2 -> 8.0;
            default -> 10.0;
        };

        double min = (16.0 - width) / 2.0;
        double max = (16.0 + width) / 2.0;

        return switch (state.getValue(FACING)) {
            case UP -> Block.box(min, 0, min, max, length, max);
            case DOWN -> Block.box(min, 16 - length, min, max, 16, max);
            case NORTH -> Block.box(min, min, 16 - length, max, max, 16);
            case SOUTH -> Block.box(min, min, 0, max, max, length);
            case EAST -> Block.box(0, min, min, length, max, max);
            case WEST -> Block.box(16 - length, min, min, 16, max, max);
        };
    }
}
