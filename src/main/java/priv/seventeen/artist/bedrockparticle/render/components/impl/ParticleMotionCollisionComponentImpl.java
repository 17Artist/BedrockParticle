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

import gg.moonflower.pinwheel.particle.component.ParticleMotionCollisionComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysics;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysicsComponent;
import gg.moonflower.pollen.particle.listener.BedrockParticleListener;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3d;

@ApiStatus.Internal
public class ParticleMotionCollisionComponentImpl extends BedrockParticleComponentImpl implements BedrockParticlePhysicsComponent, BedrockParticleListener {

    private final ParticleMotionCollisionComponent data;
    private final Vector3d acceleration;
    private final Vector3d velocity;

    public ParticleMotionCollisionComponentImpl(BedrockParticle particle, ParticleMotionCollisionComponent data) {
        super(particle);
        this.data = data;
        this.acceleration = new Vector3d();
        this.velocity = new Vector3d();
    }

    @Override
    public void physicsTick() {
        this.getPhysics().setCollision(this.particle.getEnvironment().safeResolve(this.data.enabled()) == 1);
    }

    @Override
    public void onCreate(BedrockParticle particle) {
        this.getPhysics().setCollisionRadius(this.data.collisionRadius());
    }

    @Override
    public void onCollide(BedrockParticle particle, boolean x, boolean y, boolean z) {
        BedrockParticlePhysics physics = this.getPhysics();
        physics.setSpeed(physics.getSpeed() - this.data.collisionDrag());
        if (y) {
            physics.setAcceleration(physics.getAcceleration().mul(1, -this.data.coefficientOfRestitution(), 1, this.acceleration));
            physics.setVelocity(physics.getVelocity().mul(1, -this.data.coefficientOfRestitution(), 1, this.velocity));
        }
        if (this.data.expireOnContact()) {
            particle.expire();
        }
        for (String event : this.data.events()) {
            particle.runEvent(event);
        }
    }
}
