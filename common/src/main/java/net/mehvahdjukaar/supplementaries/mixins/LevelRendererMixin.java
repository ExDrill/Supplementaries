package net.mehvahdjukaar.supplementaries.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import net.mehvahdjukaar.supplementaries.client.renderers.items.SlingshotRendererHelper;
import net.mehvahdjukaar.supplementaries.common.network.ClientReceivers;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(
            method = "renderLevel",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/client/Minecraft;hitResult:Lnet/minecraft/world/phys/HitResult;",
                    ordinal = 1
            )
    )
    private void supplementaries$renderSlingshotOutline(DeltaTracker deltaTracker, boolean blockOutlines, Camera camera,
                                                        GameRenderer gameRenderer, LightTexture lightTexture,
                                                        Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci,
                                                        @Local PoseStack poseStack) {
        if (blockOutlines) SlingshotRendererHelper.renderBlockOutline(poseStack, camera, this.minecraft);
    }

    @Inject(method = "notifyNearbyEntities", at = @At("HEAD"))
    private void supp$setPartying(Level worldIn, BlockPos pos, boolean isPartying, CallbackInfo info) {
        ClientReceivers.setDisplayParrotsPartying(worldIn, Either.right(pos), isPartying);
    }


}
