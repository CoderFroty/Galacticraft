/*
 * Copyright (c) 2019-2025 Team Galacticraft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.galacticraft.mod.client.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.galacticraft.mod.Constant;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.AbstractIllager;
import org.jetbrains.annotations.Nullable;

public class EvolvedIllagerMaskRenderLayer<T extends AbstractIllager, M extends IllagerModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation TEXTURE = Constant.id("textures/entity/gear/illager_gear.png");
    private final @Nullable ModelPart mask;
    private final @Nullable ModelPart pipe;

    public EvolvedIllagerMaskRenderLayer(RenderLayerParent<T, M> context) {
        super(context);
        ModelPart root, head, body;
        if (context.getModel() instanceof HierarchicalModel<?> model) {
            root = model.root();
            head = root.getChild(PartNames.HEAD);
            body = root.getChild(PartNames.BODY);
        } else {
            this.mask = null;
            this.pipe = null;
            return;
        }
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        if (head != null) {
            PartDefinition maskPartData = modelPartData.addOrReplaceChild(Constant.ModelPartName.OXYGEN_MASK, CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -11.01F, -5.0F, 10, 12, 10, CubeDeformation.NONE), PartPose.offset(head.x, head.y, head.z));
            maskPartData.addOrReplaceChild(Constant.ModelPartName.ILLAGER_NOSE_COMPARTMENT, CubeListBuilder.create().texOffs(10, 23).addBox(-2.0F, -4.01F, -7.0F, 4, 6, 3, CubeDeformation.NONE), PartPose.offset(head.x, head.y, head.z));
        }
        if (body != null) {
            modelPartData.addOrReplaceChild(Constant.ModelPartName.OXYGEN_PIPE, CubeListBuilder.create().texOffs(40, 6).addBox(-2.0F, -3.0F, 2.0F, 4, 6, 8, CubeDeformation.NONE), PartPose.offset(body.x, body.y, body.z));
        }

        root = modelPartData.bake(64, 32);

        if (head != null) {
            this.mask = root.getChild(Constant.ModelPartName.OXYGEN_MASK);
        } else {
            this.mask = null;
        }

        if (body != null) {
            this.pipe = root.getChild(Constant.ModelPartName.OXYGEN_PIPE);
        } else {
            this.pipe = null;
        }
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity), true));

        if (this.mask != null) {
            this.mask.yRot = headYaw * Mth.DEG_TO_RAD;
            this.mask.xRot = headPitch * Mth.DEG_TO_RAD;
            this.mask.render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        }
        if (this.pipe != null) {
            this.pipe.render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        }
    }

    @Override
    protected ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }
}
