package de.sarenor.arsinstrumentum.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import de.sarenor.arsinstrumentum.blocks.ArcaneApplicator;
import de.sarenor.arsinstrumentum.blocks.tiles.ArcaneApplicatorTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;


public class ArcaneApplicatorRenderer extends GeoBlockRenderer<ArcaneApplicatorTile> {
    public static GeoModel<ArcaneApplicatorTile> arcane_applicator_model = new GenericModel<>("arcane_applicator");

    public ArcaneApplicatorRenderer() {
        super(arcane_applicator_model);
    }

    public static GenericItemBlockRenderer getISTER() {
        return new GenericItemBlockRenderer(arcane_applicator_model);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, ArcaneApplicatorTile animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        if (animatable.getStack() == null || animatable.getStack().isEmpty()) {
            return;
        }

        poseStack.pushPose();
        var facing = animatable.getBlockState().getValue(ArcaneApplicator.FACING).getNormal();
        poseStack.translate(-facing.getX() * 0.31, 0.9, -facing.getZ() * 0.31);
        poseStack.mulPose(Axis.XP.rotationDegrees(facing.getZ() * 67));
        poseStack.mulPose(Axis.ZP.rotationDegrees(facing.getX() * 67));
        Minecraft.getInstance().getItemRenderer().renderStatic(animatable.getStack(),
                ItemDisplayContext.GROUND,
                packedLight,
                packedOverlay,
                poseStack,
                bufferSource,
                animatable.getLevel(),
                (int) animatable.getBlockPos().asLong());

        poseStack.popPose();

    }
}
