package com.zyra.armor_reforged.menu;

import com.zyra.armor_reforged.ArmorReforged;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;

public class AlloySmelterScreen extends AbstractContainerScreen<AlloySmelterMenu> {

    private static final Identifier CONTAINER_TEXTURE =
            Identifier.fromNamespaceAndPath(
                    ArmorReforged.MOD_ID,
                    "textures/gui/alloy_smelter/alloy_smelter.png"
            );

    private static final Identifier ARROW_TEXTURE =
            Identifier.fromNamespaceAndPath(
                    ArmorReforged.MOD_ID,
                    "textures/gui/alloy_smelter/alloy_smelter_arrow.png"
            );

    private static final Identifier FLAME_TEXTURE =
            Identifier.fromNamespaceAndPath(
                    ArmorReforged.MOD_ID,
                    "textures/gui/alloy_smelter/alloy_smelter_flame.png"
            );




    public AlloySmelterScreen(
            AlloySmelterMenu menu,
            Inventory inventory,
            Component title
    ) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float delta
    ) {
        super.extractBackground(graphics, mouseX, mouseY, delta);

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                CONTAINER_TEXTURE,
                this.leftPos,
                this.topPos,
                0.0F,
                0.0F,
                this.imageWidth,
                this.imageHeight,
                256,
                256
        );


                // Progress arrow
        int progress = menu.getMaxProgress() > 0
                ? menu.getProgress() * 24 / menu.getMaxProgress()
                : 0;

        if (progress > 0) {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    ARROW_TEXTURE,
                    this.leftPos + 79,
                    this.topPos + 34,
                    0, 0,
                    progress, 16,
                    24, 16

            );
        }

// Fuel flame
        int fuel = menu.getMaxBurnTime() > 0
                ? menu.getBurnTime() * 14 / menu.getMaxBurnTime()
                : 0;

        if (fuel > 0) {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    FLAME_TEXTURE,
                    this.leftPos + 56,
                    this.topPos + 51 - fuel,
                    0, 14 - fuel,
                    14, fuel,
                    14, 14
            );
        }



    }




}