package com.zyra.armor_reforged.block.entity.custom;

import com.zyra.armor_reforged.block.entity.ImplementedInventory;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class AlloySmelterBlockEntity extends BlockEntity implements ExtendedMenuProvider<BlockPos>, ImplementedInventory {
    public final NonNullList<ItemStack> inventory = NonNullList.withSize(2,ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;

    public AlloySmelterBlockEntity( BlockPos worldPosition, BlockState blockState) {
        super(, worldPosition, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int dataId){
                return switch (dataId){
                    case 0 -> AlloySmelterBlockEntity.this.progress;
                    case 1 -> AlloySmelterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int dataId, int value){
                switch (dataId){
                    case 0: AlloySmelterBlockEntity.this.progress = value;
                    case 1: AlloySmelterBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }



    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }







    @Override
    public BlockPos getScreenOpeningData(ServerPlayer serverPlayer) {
        return this.worldPosition;
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new;
    }




    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if(!level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }





}
