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
package priv.seventeen.artist.bedrockparticle.render.components.impl;

import gg.moonflower.molangcompiler.api.MolangEnvironment;
import gg.moonflower.pinwheel.particle.component.ParticleMotionDynamicComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysics;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysicsComponent;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3d;
import org.joml.Vector3dc;

@ApiStatus.Internal
public class ParticleMotionDynamicComponentImpl extends BedrockParticleComponentImpl implements BedrockParticlePhysicsComponent {

    private final ParticleMotionDynamicComponent data;
    private final Vector3d acceleration;

    public ParticleMotionDynamicComponentImpl(BedrockParticle particle, ParticleMotionDynamicComponent data) {
        super(particle);
        this.data = data;
        this.acceleration = new Vector3d();
    }

    @Override
    public void physicsTick() {
        BedrockParticlePhysics physics = this.getPhysics();
        MolangEnvironment environment = this.particle.getEnvironment();
        Vector3dc acceleration = physics.getAcceleration();
        float accelerationX = (float) (acceleration.x() + environment.safeResolve(this.data.linearAcceleration()[0])) / 400F; // 400 because 20 * 20 and the units need to be blocks/tick/tick
        float accelerationY = (float) (acceleration.y() + environment.safeResolve(this.data.linearAcceleration()[1])) / 400F;
        float accelerationZ = (float) (acceleration.z() + environment.safeResolve(this.data.linearAcceleration()[2])) / 400F;
        float drag = environment.safeResolve(this.data.linearDragCoefficient()) / 400F;
        Vector3dc velocity = physics.getVelocity();
        physics.setAcceleration(this.acceleration.set(accelerationX - drag * velocity.x(), accelerationY - drag * velocity.y(), accelerationZ - drag * velocity.z()));

        float rotationAcceleration = physics.getRotationAcceleration() + environment.safeResolve(this.data.rotationAcceleration()) / 400F;
        float rotationDrag = environment.safeResolve(this.data.rotationDragCoefficient()) / 400F;
        physics.setRotationAcceleration(rotationAcceleration - rotationDrag * physics.getRotationVelocity());
    }
}
