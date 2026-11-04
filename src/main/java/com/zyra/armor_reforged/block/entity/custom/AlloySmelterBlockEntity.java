package com.zyra.armor_reforged.block.entity.custom;

import com.zyra.armor_reforged.block.entity.ImplementedInventory;
import com.zyra.armor_reforged.block.entity.ModBlockEntities;
import com.zyra.armor_reforged.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import com.zyra.armor_reforged.menu.AlloySmelterMenu;
import net.minecraft.world.Container;


public class AlloySmelterBlockEntity extends BlockEntity  implements MenuProvider, ImplementedInventory{
    public final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int FUEL_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;

    private int burnTime = 0;
    private int maxBurnTime = 0;

    public AlloySmelterBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.ALLOY_SMELTER_BE, worldPosition, blockState);

        this.data = new ContainerData() {
            @Override
            public int get(int dataId) {
                return switch (dataId) {
                    case 0 -> AlloySmelterBlockEntity.this.progress;
                    case 1 -> AlloySmelterBlockEntity.this.maxProgress;
                    case 2 -> AlloySmelterBlockEntity.this.burnTime;
                    case 3 -> AlloySmelterBlockEntity.this.maxBurnTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int dataId, int value) {
                switch (dataId) {
                    case 0 -> AlloySmelterBlockEntity.this.progress = value;
                    case 1 -> AlloySmelterBlockEntity.this.maxProgress = value;
                    case 2 -> AlloySmelterBlockEntity.this.burnTime = value;
                    case 3 -> AlloySmelterBlockEntity.this.maxBurnTime = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }


    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("alloy_smelter.progress", progress);
        output.putInt("alloy_smelter.max_progress", maxProgress);
        output.putInt("alloy_smelter.burn_time", burnTime);
        output.putInt("alloy_smelter.max_burn_time", maxBurnTime);

        ContainerHelper.saveAllItems(output, inventory);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        progress = input.getIntOr("alloy_smelter.progress", 0);
        maxProgress = input.getIntOr("alloy_smelter.max_progress", 72);
        burnTime = input.getIntOr("alloy_smelter.burn_time", 0);
        maxBurnTime = input.getIntOr("alloy_smelter.max_burn_time", 0);

        ContainerHelper.loadAllItems(input, inventory);
    }

    public void drops() {
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.armor_reforged.alloy_smelter");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(
            int containerId, Inventory inventory, Player player) {
        return new AlloySmelterMenu(containerId, inventory, this, this.data);
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    public static void tick(
            Level level,
            BlockPos pos,
            BlockState state,
            AlloySmelterBlockEntity entity
    ) {
        boolean wasBurning = entity.burnTime > 0;

        if (entity.burnTime > 0) {
            entity.burnTime--;
        }

        if (entity.hasRecipe()) {

            if (entity.burnTime == 0 && entity.hasFuel()) {
                entity.maxBurnTime = entity.getFuelTime();
                entity.burnTime = entity.maxBurnTime;
                entity.consumeFuel();
            }

            if (entity.burnTime > 0) {
                entity.progress++;

                if (entity.progress >= entity.maxProgress) {
                    entity.craftItem();
                    entity.progress = 0;
                }
            } else {
                entity.progress = 0;
            }

        } else {
            entity.progress = 0;
        }

        if (wasBurning != (entity.burnTime > 0)
                || entity.burnTime > 0
                || entity.progress > 0) {
            entity.setChanged();
        }
    }

    private boolean hasRecipe() {
        ItemStack output = new ItemStack(ModItems.STEEL_INGOT);

        boolean hasInput1 = inventory.get(INPUT_SLOT_1).is(Items.IRON_INGOT);
        boolean hasInput2 = inventory.get(INPUT_SLOT_2).is(Items.COAL);

        boolean outputIsValid = inventory.get(OUTPUT_SLOT).isEmpty()
                || inventory.get(OUTPUT_SLOT).is(output.getItem());

        boolean hasOutputSpace = inventory.get(OUTPUT_SLOT).getCount()
                + output.getCount() <= inventory.get(OUTPUT_SLOT).getMaxStackSize();

        return hasInput1 && hasInput2 && outputIsValid && hasOutputSpace;
    }
    private boolean hasFuel() {
        return getFuelTime() > 0;
    }

    private int getFuelTime() {
        if (level == null) {
            return 0;
        }

        return level.fuelValues().burnDuration(inventory.get(FUEL_SLOT));
    }

    private void consumeFuel() {
        ItemStack fuel = inventory.get(FUEL_SLOT);

        if (fuel.is(Items.LAVA_BUCKET)) {
            inventory.set(FUEL_SLOT, new ItemStack(Items.BUCKET));
        } else {
            fuel.shrink(1);

            if (fuel.isEmpty()) {
                inventory.set(FUEL_SLOT, ItemStack.EMPTY);
            }
        }
    }






    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = inventory.get(OUTPUT_SLOT).isEmpty() ? 64 : inventory.get(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = inventory.get(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return inventory.get(OUTPUT_SLOT).isEmpty() ||
                inventory.get(OUTPUT_SLOT).is(output.getItem());
    }


    private void craftItem() {
        inventory.get(INPUT_SLOT_1).shrink(1);
        inventory.get(INPUT_SLOT_2).shrink(1);

        ItemStack output = inventory.get(OUTPUT_SLOT);

        if (output.isEmpty()) {
            inventory.set(OUTPUT_SLOT, new ItemStack(ModItems.STEEL_INGOT));
        } else {
            output.grow(1);
        }
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return inventory.get(OUTPUT_SLOT).isEmpty() ||
                inventory.get(OUTPUT_SLOT).getCount() < inventory.get(OUTPUT_SLOT).getMaxStackSize();
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void resetProgress() {
        progress = 0;
        maxProgress = 72;
    }

    /* BLOCK ENTITY SYNC METHODS */
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