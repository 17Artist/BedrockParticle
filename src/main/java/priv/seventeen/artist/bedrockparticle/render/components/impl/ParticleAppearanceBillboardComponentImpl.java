/*
 * Copyright (C) 2023 Ocelot
 * Licensed under GNU Lesser General Public License v3.0
 *
 * Minor modifications by 17Artist (2025-3-29)
 *
 * Changes:
 * - Renamed package from ‘gg.moonflower.pollen.*’  to 'priv.seventeen.artist' (all subpackages)
 * - Implemented LOOKAT_DIRECTION mode and emitter rotation
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package priv.seventeen.artist.bedrockparticle.render.components.impl;

import com.mojang.math.Axis;
import gg.moonflower.molangcompiler.api.MolangEnvironment;
import gg.moonflower.molangcompiler.api.MolangExpression;
import gg.moonflower.pinwheel.particle.component.ParticleAppearanceBillboardComponent;
import gg.moonflower.pollen.particle.render.QuadRenderProperties;
import gg.moonflower.pollen.particle.BedrockParticle;
import org.joml.*;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysics;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleRenderComponent;
import gg.moonflower.pollen.particle.listener.BedrockParticleListener;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.ApiStatus;

import java.lang.Math;

@ApiStatus.Internal
public class ParticleAppearanceBillboardComponentImpl extends BedrockParticleComponentImpl implements BedrockParticleListener, BedrockParticleRenderComponent {

    private final ParticleAppearanceBillboardComponent data;

    public ParticleAppearanceBillboardComponentImpl(BedrockParticle particle, ParticleAppearanceBillboardComponent data) {
        super(particle);
        this.data = data;
    }

    private static QuadRenderProperties getRenderProperties(BedrockParticle particle) {
        if (particle.getRenderProperties() != null) {
            return particle.getRenderProperties();
        }
        QuadRenderProperties properties = new QuadRenderProperties();
        particle.setRenderProperties(properties);
        return properties;
    }

    @Override
    public void render(Camera camera, float partialTicks) {
        MolangEnvironment environment = this.particle.getEnvironment();
        QuadRenderProperties renderProperties = getRenderProperties(particle);
        renderProperties.setWidth(environment.safeResolve(this.data.size()[0]));
        renderProperties.setHeight(environment.safeResolve(this.data.size()[1]));
        this.data.textureSetter().setUV(this.particle, environment, renderProperties);

        // 运算粒子位置
        Vector3dc pos = this.particle.position(partialTicks);

        switch (this.data.cameraMode()) {
            case ROTATE_XYZ -> renderProperties.getRotation().set(camera.rotation());
            case ROTATE_Y ->{
                renderProperties.getRotation().setAngleAxis((float) -(camera.getYRot() * Math.PI / 180.0F), 0, 1, 0);
            }
            case LOOK_AT_XYZ -> {
                double dx = camera.getPosition().x() - pos.x();
                double dy = camera.getPosition().y() - pos.y();
                double dz = camera.getPosition().z() - pos.z();
                float yRot = (float) Mth.atan2(dz, dx);
                float xRot = (float) Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz));
                Quaternionf rotation = renderProperties.getRotation().identity();
                rotation.rotateZYX(0, -((float) (Math.PI / 2F) + yRot), xRot);
            }
            case LOOK_AT_Y -> {
                double dx = camera.getPosition().x() - pos.x();
                double dz = camera.getPosition().z() - pos.z();
                float yRot = (float) Mth.atan2(dz, dx);
                Quaternionf rotation = renderProperties.getRotation().identity();
                rotation.rotateY(-((float) (Math.PI / 2F) + yRot));
            }
            case DIRECTION_X -> {
                renderProperties.setDirection(true);
                Vector3dc direction = getDirection();
                if(direction == null){
                    return;
                }
                double dx = direction.x();
                double dy = direction.y();
                double dz = direction.z();

                float yRot = (float) Mth.atan2(dz, dx);
                float xRot = (float) Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz));
                Quaternionf rotation = renderProperties.getRotation().identity();
                rotation.rotateZYX(0, -yRot, xRot);
            }
            case DIRECTION_Y -> {
                renderProperties.setDirection(true);
                Vector3dc direction = getDirection();
                if(direction == null){
                    return;
                }
                double dx = direction.x();
                double dy = direction.y();
                double dz = direction.z();

                float yRot = (float) Mth.atan2(dz, dx);
                float xRot = (float) Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz));
                Quaternionf rotation = renderProperties.getRotation().identity();
                rotation.rotateZYX(0, -(yRot - (float) (Math.PI / 2F)), -(xRot - (float) (Math.PI / 2F)));
            }
            case DIRECTION_Z -> {
                renderProperties.setDirection(true);
                Vector3dc direction = getDirection();
                if(direction == null){
                    return;
                }
                double dx = direction.x();
                double dy = direction.y();
                double dz = direction.z();

                float yRot = (float) Mth.atan2(dz, dx);
                float xRot = (float) Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz));
                Quaternionf rotation = renderProperties.getRotation().identity();

                rotation.rotateZYX(0, -((float) (Math.PI / 2F) + yRot), xRot);

            }
            case LOOKAT_DIRECTION -> {
                renderProperties.setDirection(true);
                Vector3dc direction_ = getDirection();
                if(direction_ == null){
                    return;
                }

                double dx = direction_.x();
                double dy = direction_.y();
                double dz = direction_.z();

                float yRot = (float) Mth.atan2(dz, dx);
                float xRot = (float) Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz));

                Quaternionf rotation = renderProperties.getRotation().identity();
                rotation.rotateY(-yRot);
                rotation.rotateX(xRot + (float)(Math.PI/2));

                Vector3f cameraDir = new Vector3f(
                        (float) (camera.getPosition().x - pos.x()),
                        (float) (camera.getPosition().y - pos.y()),
                        (float) (camera.getPosition().z - pos.z()));

                Vector3f rotatedNormal = new Vector3f(0, 0, 1);
                rotation.transform(rotatedNormal);

                Vector3f direction = new Vector3f((float) dx, (float) dy, (float) dz);
                direction.normalize();

                Vector3f projectDir = new Vector3f(direction);
                projectDir.mul(cameraDir.dot(direction));
                cameraDir.sub(projectDir);

                cameraDir.normalize();

                Vector3f rotationDirection = new Vector3f();
                rotationDirection.cross(cameraDir, rotatedNormal);

                float finalRotAngle = -Math.copySign(cameraDir.angle(rotatedNormal), rotationDirection.dot(direction));
                rotation.rotateY(finalRotAngle);
            }
        }
    }



    @Override
    public void onCreate(BedrockParticle particle) {
        QuadRenderProperties renderProperties = getRenderProperties(particle);
        switch (this.data.cameraMode()) {
            case EMITTER_TRANSFORM_XZ ->
            {
                renderProperties.getRotation().set(Axis.XP.rotationDegrees(90));
                renderProperties.setDirection(true);
            }
            case EMITTER_TRANSFORM_YZ ->
            {
                renderProperties.getRotation().set(Axis.YP.rotationDegrees(90));
                renderProperties.setDirection(true);
            }
            case EMITTER_TRANSFORM_XY -> {
                renderProperties.setDirection(true);
            }
        }
    }


    private Vector3dc getDirection(){
        BedrockParticlePhysics physics = this.particle.getPhysics();
        if (physics == null) {
            return null;
        }
        MolangEnvironment environment = this.particle.getEnvironment();

        // 获取粒子的方向向量
        double dx, dy, dz;
        MolangExpression[] customDirection = this.data.customDirection();
        if (customDirection != null) {
            dx = environment.safeResolve(customDirection[0]);
            dy = environment.safeResolve(customDirection[1]);
            dz = environment.safeResolve(customDirection[2]);
        } else {
            Vector3dc direction = physics.getDirection();
            int factor = physics.getSpeed() > this.data.minSpeedThreshold() ? 1 : 0;
            dx = factor * direction.x();
            dy = factor * direction.y();
            dz = factor * direction.z();
        }

        return new Vector3d(dx, dy, dz);
    }




}
