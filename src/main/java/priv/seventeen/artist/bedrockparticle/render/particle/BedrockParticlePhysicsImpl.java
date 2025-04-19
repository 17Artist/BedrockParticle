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
package priv.seventeen.artist.bedrockparticle.render.particle;

import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysics;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3d;
import org.joml.Vector3dc;

@ApiStatus.Internal
public class BedrockParticlePhysicsImpl implements BedrockParticlePhysics {

    private final Vector3d direction;
    private float speed;
    private final Vector3d velocity;
    private final Vector3d acceleration;

    private float rotationVelocity;
    private float rotationAcceleration;
    private float collisionRadius;
    private boolean collision;

    public BedrockParticlePhysicsImpl() {
        this.direction = new Vector3d();
        this.speed = 0;
        this.velocity = new Vector3d();
        this.acceleration = new Vector3d();

        this.rotationVelocity = 0;
        this.rotationAcceleration = 0;
        this.collisionRadius = 0.1F;
        this.collision = true;
    }

    public void tick() {
        if (this.acceleration.lengthSquared() > 1.0E-7) {
            this.setVelocity(this.getVelocity().add(this.acceleration, this.velocity));
        }
    }

    @Override
    public Vector3dc getDirection() {
        return this.direction;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public Vector3dc getVelocity() {
        return this.direction.mul(this.speed, this.velocity);
    }

    @Override
    public Vector3dc getAcceleration() {
        return this.acceleration;
    }

    @Override
    public float getRotationVelocity() {
        return this.rotationVelocity;
    }

    @Override
    public float getRotationAcceleration() {
        return this.rotationAcceleration;
    }

    @Override
    public boolean hasCollision() {
        return this.collision;
    }

    @Override
    public float getCollisionRadius() {
        return this.collisionRadius;
    }

    @Override
    public void setDirection(double dx, double dy, double dz) {
        this.direction.set(dx, dy, dz);

        double lengthSq = this.direction.lengthSquared();
        if (lengthSq != 0) { // 0 causes division by zero
            this.direction.normalize();
        }
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = Math.max(speed, 0);
    }

    @Override
    public void setAcceleration(Vector3dc acceleration) {
        this.acceleration.set(acceleration);
    }

    @Override
    public void setRollVeclocity(float velocity) {
        this.rotationVelocity = velocity;
    }

    @Override
    public void setRotationAcceleration(float acceleration) {
        this.rotationAcceleration = acceleration;
    }

    @Override
    public void setCollision(boolean enabled) {
        this.collision = enabled;
    }

    @Override
    public void setCollisionRadius(float radius) {
        this.collisionRadius = radius;
    }
}
