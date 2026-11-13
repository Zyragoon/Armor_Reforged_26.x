package com.zyra.armor_reforged.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;
import com.zyra.armor_reforged.block.entity.ModBlockEntities;
import com.zyra.armor_reforged.block.entity.custom.AlloySmelterBlockEntity;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;

public class AlloySmelterBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<AlloySmelterBlock> CODEC = simpleCodec(AlloySmelterBlock::new);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;


    public AlloySmelterBlock(Properties properties) {
        super(properties);

        registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(LIT, false)
        );
    }

    /* FACING */

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING,context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(FACING, LIT);
    }

    /* BLOCK ENTITY */
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new AlloySmelterBlockEntity(worldPosition, blockState);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack destroyedWith) {
        super.playerDestroy(level, player, pos, state, blockEntity, destroyedWith);
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult
    ) {
        if (!level.isClientSide()
                && level.getBlockEntity(pos) instanceof AlloySmelterBlockEntity smelter) {
            player.openMenu(smelter);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level,
            BlockState blockState,
            BlockEntityType<T> type
    ) {
        if (level.isClientSide()) {
            return null;
        }

        return createTickerHelper(
                type,
                ModBlockEntities.ALLOY_SMELTER_BE,
                AlloySmelterBlockEntity::tick
        );
    }

    @Override
    public void animateTick(
            BlockState state,
            Level level,
            BlockPos pos,
            RandomSource random
    ) {
        if (!state.getValue(LIT)) {
            return;
        }

        if (random.nextDouble() < 0.6) {
            Direction facing = state.getValue(FACING);

            double x = pos.getX() + 0.5 + facing.getStepX() * 0.52;
            double y = pos.getY() + 0.4 + random.nextDouble() * 0.3;
            double z = pos.getZ() + 0.5 + facing.getStepZ() * 0.52;
            if (random.nextDouble()< 0.15){
                level.playLocalSound(x, y, z, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f,false);
            }

            level.addParticle(
                    ParticleTypes.SMOKE,
                    x, y, z,
                    0.0, 0.0, 0.0
            );

            level.addParticle(
                    ParticleTypes.FLAME,
                    x, y, z,
                    0.0, 0.0, 0.0
            );
        }
    }

}
