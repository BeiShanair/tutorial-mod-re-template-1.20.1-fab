package com.besson.tutorial.block;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SeatRenderer extends EntityRenderer<SeatEntity> {
    // 记住实体类都要设置其渲染器

    public SeatRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(SeatEntity entity) {
        return null;
    }

    // 重写renderLabelIfPresent，什么也不写
    @Override
    protected void renderLabelIfPresent(SeatEntity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

    }
}
