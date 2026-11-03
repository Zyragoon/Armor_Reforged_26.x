package com.zyra.armor_reforged.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import com.zyra.armor_reforged.block.entity.ImplementedInventory;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AlloySmelterMenu extends AbstractContainerMenu {

    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int FUEL_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;

    private static final int MACHINE_SLOT_COUNT = 4;
    private static final int PLAYER_INVENTORY_START = MACHINE_SLOT_COUNT;
    private static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + 36;

    private final Container container;
    private final ContainerData data;

    // Client-side constructor
    public AlloySmelterMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory,
                ImplementedInventory.ofSize(MACHINE_SLOT_COUNT),
                new SimpleContainerData(4));
    }

    // Server-side constructor
    public AlloySmelterMenu(int containerId, Inventory playerInventory,
                            Container container, ContainerData data) {
        super(ModMenuTypes.ALLOY_SMELTER, containerId);

        checkContainerSize(container, MACHINE_SLOT_COUNT);

        this.container = container;
        this.data = data;

        container.startOpen(playerInventory.player);

        // Input slot 1
        this.addSlot(new Slot(container, INPUT_SLOT_1, 44, 35));

        // Input slot 2
        this.addSlot(new Slot(container, INPUT_SLOT_2, 62, 35));

        // Fuel slot
        this.addSlot(new Slot(container, FUEL_SLOT, 44, 57) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isFuel(stack);
            }
        });

        // Output slot
        this.addSlot(new Slot(container, OUTPUT_SLOT, 116, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // Player inventory and hotbar
        this.addStandardInventorySlots(playerInventory, 8, 84);

        // Synchronize progress values
        this.addDataSlots(data);
    }

    private static boolean isFuel(ItemStack stack) {
        return stack.is(Items.COAL) || stack.is(Items.CHARCOAL);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        Slot slot = this.slots.get(slotIndex);

        if (slot == null || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack originalStack = stack.copy();

        if (slotIndex < MACHINE_SLOT_COUNT) {
            // Move machine items into the player's inventory
            if (!this.moveItemStackTo(
                    stack,
                    PLAYER_INVENTORY_START,
                    PLAYER_INVENTORY_END,
                    true
            )) {
                return ItemStack.EMPTY;
            }
        } else {
            // Move fuel into the fuel slot first
            if (isFuel(stack) && !this.moveItemStackTo(
                    stack, FUEL_SLOT, FUEL_SLOT + 1, false
            )) {
                return ItemStack.EMPTY;
            }

            // Move remaining items into the two input slots
            if (!stack.isEmpty() && !this.moveItemStackTo(
                    stack, INPUT_SLOT_1, FUEL_SLOT, false
            )) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return originalStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public int getProgress() {
        return this.data.get(0);
    }

    public int getMaxProgress() {
        return this.data.get(1);
    }
}