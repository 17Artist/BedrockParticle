/*
 * Copyright (C) 2023 Ocelot
 * Licensed under GNU Lesser General Public License v3.0
 *
 * Minor modifications by 17Artist (2025-3-29)
 *
 * Changes:
 * - Renamed package from ‘gg.moonflower.pollen.*’  to 'priv.seventeen.artist' (all subpackages)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package priv.seventeen.artist.bedrockparticle.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import priv.seventeen.artist.bedrockparticle.render.particle.instance.BedrockParticleInstanceImpl;

import java.util.Map;
import java.util.Queue;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {

    @Shadow
    @Final
    private Map<ParticleRenderType, Queue<Particle>> particles;
    @Shadow
    @Final
    private TextureManager textureManager;



    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LightTexture;turnOffLightLayer()V",ordinal = 0))
    public void renderPost(PoseStack poseStack,
                           MultiBufferSource.BufferSource bufferSource,
                           LightTexture lightTexture,
                           Camera camera,
                           float partialTicks,
                           Frustum clippingHelper,CallbackInfo ci) {
        RenderSystem.enableDepthTest();
        PoseStack poseStack2 = RenderSystem.getModelViewStack();
        poseStack2.pushPose();
        poseStack2.mulPoseMatrix(poseStack.last().pose());
        RenderSystem.applyModelViewMatrix();

        Iterable<Particle> iterable = this.particles.get(BedrockParticleInstanceImpl.GEOMETRY_SHEET);
        if (iterable != null) {
            RenderSystem.setShader(GameRenderer::getParticleShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            BedrockParticleInstanceImpl.GEOMETRY_SHEET.begin(bufferBuilder, this.textureManager);

            for (Particle particle : iterable) {
                try {
                    particle.render(bufferBuilder, camera, partialTicks);
                } catch (Throwable var17) {
                    CrashReport crashReport = CrashReport.forThrowable(var17, "Rendering Particle");
                    CrashReportCategory crashReportCategory = crashReport.addCategory("Particle being rendered");
                    crashReportCategory.setDetail("Particle", particle::toString);
                    crashReportCategory.setDetail("Particle Type",BedrockParticleInstanceImpl.GEOMETRY_SHEET::toString);
                    throw new ReportedException(crashReport);
                }
            }

            BedrockParticleInstanceImpl.GEOMETRY_SHEET.end(tesselator);
        }

        poseStack2.popPose();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();

    }
}
