/*
 * Copyright (C) 2023 Ocelot
 * Licensed under GNU Lesser General Public License v3.0
 *
 * Minor modifications by 17Artist (2025-3-29)
 *
 * Changes:
 * - Renamed package from ‘gg.moonflower.pollen.*’  to 'priv.seventeen.artist' (all subpackages)
 * - Added implementation for rotation and following features
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package priv.seventeen.artist.bedrockparticle.render.particle.instance;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import gg.moonflower.molangcompiler.api.MolangEnvironment;
import gg.moonflower.molangcompiler.api.MolangEnvironmentBuilder;
import gg.moonflower.molangcompiler.api.bridge.MolangVariableProvider;
import gg.moonflower.pinwheel.particle.ParticleData;
import gg.moonflower.pinwheel.particle.component.ParticleComponent;
import gg.moonflower.pollen.particle.render.QuadRenderProperties;
import gg.moonflower.pinwheel.particle.transform.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;
import priv.seventeen.artist.bedrockparticle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.type.BedrockParticleComponentFactory;
import gg.moonflower.pollen.particle.BedrockParticleEmitter;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleComponent;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleRenderComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3dc;
import priv.seventeen.artist.bedrockparticle.render.rendertype.BedrockParticleRenderType;

import java.util.*;

/**
 * @author Ocelot
 */
@ApiStatus.Internal
public class BedrockParticleInstanceImpl extends BedrockParticleImpl {

    public static final ParticleGroup GROUP = new ParticleGroup(10000);
    public static final ParticleRenderType GEOMETRY_SHEET = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
        }

        @Override
        public void end(Tesselator tesselator) {
            Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
            RenderSystem.enableDepthTest();
            Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
        }

        public String toString() {
            return "GEOMETRY_SHEET";
        }
    };
    private static final MatrixStack MATRIX_STACK = new MatrixStack();
    private static final MultiBufferSource BUFFER_SOURCE = Minecraft.getInstance().renderBuffers().bufferSource();
    private static final Matrix4f POSITION = new Matrix4f();
    private final BedrockParticleEmitterImpl emitter;
    private final Set<BedrockParticleRenderComponent> renderComponents;

    @Nullable
    private QuadRenderProperties renderProperties;

    public BedrockParticleInstanceImpl(BedrockParticleEmitterImpl emitter, ClientLevel clientLevel, double x, double y, double z) {
        super(clientLevel, x, y, z, emitter.getName());
        this.emitter = emitter;
        this.renderComponents = new HashSet<>();
        this.addComponents();
        MolangEnvironmentBuilder<? extends MolangEnvironment> builder = this.environment.edit();
        builder.copy(emitter.getEnvironment()); // create copies of state variables
        builder.setVariables(emitter); // copy references from emitter
        builder.setVariables(this.curves);
        builder.setVariables(this);
    }

    @Override
    protected @Nullable BedrockParticleComponent addComponent(BedrockParticleComponentFactory<?> type, ParticleComponent data) {
        BedrockParticleComponent component = super.addComponent(type, data);
        if (component instanceof BedrockParticleRenderComponent listener) {
            this.renderComponents.add(listener);
        }
        return component;
    }

    @Override
    public void tick() {
        super.tick();
        if ((float) this.age / 20.0F >= this.lifetime.getValue()) {
            this.remove();
        }
    }

    @Override
    public void render(@NotNull VertexConsumer vertexConsumer, @NotNull Camera camera, float partialTicks) {
        if (this.age < 0) {
            return;

        }
        ProfilerFiller profiler = this.level.getProfiler();
        profiler.push("pollen");

        this.renderAge.setValue((this.age + partialTicks) / 20F);
        this.curves.evaluate(this.getEnvironment(), profiler);

        profiler.push("components");
        this.renderComponents.forEach(component -> component.render(camera, partialTicks));
        profiler.pop();



        if (this.renderProperties != null) {
            profiler.push("tessellate");
            MATRIX_STACK.pushMatrix();
            if(emitter.isRelativeRotation() && emitter.getTarget() != null){
                emitter.yaw = emitter.getTarget().getYaw(partialTicks) + 180;
                emitter.pitch = emitter.getTarget().getPitch(partialTicks);
            }
            if(this.renderProperties.isDirection()){
                Vec3 cameraPos = camera.getPosition();


                Vector3dc emitterPos = this.getEmitter().position(partialTicks);
                float emitterX = (float) (emitterPos.x() - cameraPos.x());
                float emitterY = (float) (emitterPos.y() - cameraPos.y());
                float emitterZ = (float) (emitterPos.z() - cameraPos.z());

                if(emitter.isRelativePosition() && emitter.getTarget() != null){
                    // 求发射器和实体位置差进行移动
                    double targetX = emitter.getTarget().getX(partialTicks);
                    double targetY = emitter.getTarget().getY(partialTicks);
                    double targetZ = emitter.getTarget().getZ(partialTicks);

                    MATRIX_STACK.translate(targetX - emitterPos.x(), targetY - emitterPos.y(), targetZ - emitterPos.z());
                }


                Vector3dc particlePos = this.position(partialTicks);
                float relativeX = (float) (particlePos.x() - emitterPos.x());
                float relativeY = (float) (particlePos.y() - emitterPos.y());
                float relativeZ = (float) (particlePos.z() - emitterPos.z());

                MATRIX_STACK.translate(emitterX, emitterY, emitterZ);

                float emitterYaw = this.emitter.yaw;
                float emitterPitch = this.emitter.pitch;

                MATRIX_STACK.rotate(Axis.YP.rotationDegrees(-emitterYaw));
                MATRIX_STACK.rotate(Axis.XP.rotationDegrees(-emitterPitch));



                MATRIX_STACK.translate(relativeX, relativeY, relativeZ);

            } else {
                Vector3dc pos = this.position(partialTicks);
                Vector3dc emitterPos = this.emitter.position(partialTicks);
                float particleRelativeX = (float) (pos.x() - emitterPos.x());
                float particleRelativeY = (float) (pos.y() - emitterPos.y());
                float particleRelativeZ = (float) (pos.z() - emitterPos.z());
                if(emitter.isRelativePosition() && emitter.getTarget() != null) {
                    // 求发射器和实体位置差进行移动
                    double targetX = emitter.getTarget().getX(partialTicks);
                    double targetY = emitter.getTarget().getY(partialTicks);
                    double targetZ = emitter.getTarget().getZ(partialTicks);

                    MATRIX_STACK.translate(targetX - emitterPos.x(), targetY - emitterPos.y(), targetZ - emitterPos.z());
                }
                float emitterYaw = (float) Math.toRadians(emitter.yaw);
                float emitterPitch = (float) Math.toRadians(emitter.pitch);
                Matrix4f rotationMatrix = new Matrix4f().identity();
                Matrix4f tempMatrix = new Matrix4f().identity();
                tempMatrix.identity().rotateY(-emitterYaw);
                rotationMatrix.mul(tempMatrix);
                tempMatrix.identity().rotateX(-emitterPitch);
                rotationMatrix.mul(tempMatrix);

                Vector4f particlePos = new Vector4f(particleRelativeX, particleRelativeY, particleRelativeZ, 1.0f);
                rotationMatrix.transform(particlePos);

                float finalX = (float) (particlePos.x + emitterPos.x());
                float finalY = (float) (particlePos.y + emitterPos.y());
                float finalZ = (float) (particlePos.z + emitterPos.z());


                Vec3 cameraPos = camera.getPosition();
                float x = (float) (finalX - cameraPos.x());
                float y = (float) (finalY - cameraPos.y());
                float z = (float) (finalZ - cameraPos.z());
                MATRIX_STACK.translate(x, y, z);
            }

            if (this.renderProperties.canRender()) {
                float zRot = Mth.lerp(partialTicks, this.oRoll, this.roll);
                MATRIX_STACK.translate(0, 0.01, 0);
                MATRIX_STACK.rotate(this.renderProperties.getRotation());
                MATRIX_STACK.rotate((float) (zRot * Math.PI / 180.0F), 0, 0, 1);
                MATRIX_STACK.scale(this.renderProperties.getWidth(), this.renderProperties.getHeight(), 1.0F);
                this.render(this.renderProperties);
            }
            MATRIX_STACK.popMatrix();
            profiler.pop();
        }

        profiler.pop();
    }

    private void render(QuadRenderProperties properties) {
        ParticleData.Description description = this.data.description();

        this.renderQuad(BUFFER_SOURCE.getBuffer(BedrockParticleRenderType.get(description.texture())), properties);

        // 懒得实现了 那就不支持好了

//        if (description.material() == null) {
//            return;
//        }

//        ResourceLocation location = MATERIALS.computeIfAbsent(description.material(), ResourceLocation::tryParse);
//        if (location == null) {
//            return;
//        }
//
//        TextureTable table = GeometryTextureManager.getTextures(location);
//        ModelTexture[] textures = table.getLayerTextures("texture");
//        if (textures.length == 0) {
//            return;
//        }
//
//        if (textures.length == 1) {
//            this.renderQuad(BUFFER_SOURCE.getBuffer(textures[0]), properties);
//        }
//
//        this.renderQuad(VertexMultiConsumer.create(Arrays.stream(textures).map(BUFFER_SOURCE::getBuffer).toArray(VertexConsumer[]::new)), properties);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return GEOMETRY_SHEET;
    }

    @Override
    public @NotNull Optional<ParticleGroup> getParticleGroup() {
        return Optional.empty();
    }

    private void renderQuad(VertexConsumer consumer, QuadRenderProperties properties) {
        float uMin = properties.getUMin();
        float uMax = properties.getUMax();
        float vMin = properties.getVMin();
        float vMax = properties.getVMax();
        float r = properties.getRed();
        float g = properties.getGreen();
        float b = properties.getBlue();
        float a = properties.getAlpha();
        int light = properties.getPackedLight();


        Matrix4f matrix4f =  POSITION.set(MATRIX_STACK.position());

        consumer.vertex(matrix4f, -1.0F, -1.0F, 0.0F);
        consumer.uv(uMax, vMax);
        consumer.color(r, g, b, a);
        consumer.uv2(light);
        consumer.endVertex();

        consumer.vertex(matrix4f, -1.0F, 1.0F, 0.0F);
        consumer.uv(uMax, vMin);
        consumer.color(r, g, b, a);
        consumer.uv2(light);
        consumer.endVertex();

        consumer.vertex(matrix4f, 1.0F, 1.0F, 0.0F);
        consumer.uv(uMin, vMin);
        consumer.color(r, g, b, a);
        consumer.uv2(light);
        consumer.endVertex();

        consumer.vertex(matrix4f, 1.0F, -1.0F, 0.0F);
        consumer.uv(uMin, vMax);
        consumer.color(r, g, b, a);
        consumer.uv2(light);
        consumer.endVertex();
    }

    @Override
    protected Component getPrefix() {
        return Component.empty().append(Component.literal("[Particle]").withStyle(ChatFormatting.YELLOW)).append(super.getPrefix());
    }

    @Override
    public void addMolangVariables(MolangVariableProvider.Context context) {
        context.addVariable("particle_age", this.renderAge);
        context.addVariable("particle_lifetime", this.lifetime);
        context.addVariable("particle_random_1", this.random1);
        context.addVariable("particle_random_2", this.random2);
        context.addVariable("particle_random_3", this.random3);
        context.addVariable("particle_random_4", this.random4);
    }

    @Override
    public @Nullable QuadRenderProperties getRenderProperties() {
        return renderProperties;
    }

    @Override
    public void setRenderProperties(@Nullable QuadRenderProperties properties) {
        this.renderProperties = properties;
    }

    @Override
    public BedrockParticleEmitter getEmitter() {
        return this.emitter;
    }
}
