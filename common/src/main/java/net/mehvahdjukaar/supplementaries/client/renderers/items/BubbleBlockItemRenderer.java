package net.mehvahdjukaar.supplementaries.client.renderers.items;


import com.mojang.blaze3d.vertex.PoseStack;
import net.mehvahdjukaar.moonlight.api.client.ItemStackRenderer;
import net.mehvahdjukaar.supplementaries.SupplementariesClient;
import net.mehvahdjukaar.supplementaries.client.ModMaterials;
import net.mehvahdjukaar.supplementaries.client.renderers.RendererUtil;
import net.mehvahdjukaar.supplementaries.common.events.ClientEvents;
import net.mehvahdjukaar.supplementaries.reg.ClientRegistry;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;


public class BubbleBlockItemRenderer extends ItemStackRenderer {

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack,
                             MultiBufferSource buffer, int light, int combinedOverlayIn) {

        poseStack.pushPose();

        TextureAtlasSprite sprite = ModMaterials.BUBBLE_BLOCK_MATERIAL.sprite();
        poseStack.translate(0.5, 0.5, 0.5);
        RendererUtil.renderBubble(buffer.getBuffer(RenderType.translucent()), poseStack, 1, sprite, light,
                false, BlockPos.ZERO, null, SupplementariesClient.getPartialTicks());

        poseStack.popPose();

    }
}