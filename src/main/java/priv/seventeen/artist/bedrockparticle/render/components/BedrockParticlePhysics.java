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

package priv.seventeen.artist.bedrockparticle.render.components;

import org.joml.Vector3dc;

/**
 * Physics that dictate how particles move in the world.
 *
 * @author Ocelot
 * @since 2.0.0
 */
public interface BedrockParticlePhysics {

    /**
     * @return The normalized direction this particle is travelling
     */
    Vector3dc getDirection();

    /**
     * @return The speed of motion
     */
    float getSpeed();

    /**
     * @return The current velocity of this particle in blocks/tick
     */
    Vector3dc getVelocity();

    /**
     * @return The cuurrent acceleration of this particle in blocks/tick/tick
     */
    Vector3dc getAcceleration();

    /**
     * @return The velocity of rotation in degrees/tick
     */
    float getRotationVelocity();

    /**
     * @return The acceleration of rotation in degrees/tick/tick
     */
    float getRotationAcceleration();

    /**
     * @return Whether this particle can collide with the environment
     */
    boolean hasCollision();

    /**
     * @return The radis of the box used to calculate collisions
     */
    float getCollisionRadius();

    /**
     * Sets the velocity of this particle in blocks/tick
     *
     * @param velocity The new velocity
     */
    default void setVelocity(Vector3dc velocity) {
        this.setDirection(velocity);
        this.setSpeed((float) velocity.length());
    }

    /**
     * Sets the direction this particle will travel in.
     *
     * @param direction The direction to travel. This is expected to be normalized
     */
    default void setDirection(Vector3dc direction) {
        this.setDirection(direction.x(), direction.y(), direction.z());
    }

    /**
     * Sets the direction this particle will travel in.
     *
     * @param dx The direction to travel in the x
     * @param dy The direction to travel in the y
     * @param dz The direction to travel in the z
     */
    void setDirection(double dx, double dy, double dz);

    /**
     * Sets the speed of this particle.
     *
     * @param speed The speed to set the particle to
     */
    void setSpeed(float speed);

    /**
     * Sets the acceleration of this particle in blocks/tick/tick
     *
     * @param acceleration The new acceleration
     */
    void setAcceleration(Vector3dc acceleration);

    /**
     * Sets how fast this particle will roll in degrees/tick.
     *
     * @param velocity The velocity of roll
     */
    void setRollVeclocity(float velocity);

    /**
     * Sets how fast this particle will accelerate rolling in degrees/tick/tick.
     *
     * @param acceleration The acceleration of roll
     */
    void setRotationAcceleration(float acceleration);

    /**
     * Sets whether collisions will be calculated for this particle.
     *
     * @param enabled Whether to calculate collisions
     */
    void setCollision(boolean enabled);

    /**
     * Sets the size of the bounding box on this particle. Only applies if {@link #hasCollision()} is <code>true</code>.
     *
     * @param radius The radius of the box used to calculate collisions.
     */
    void setCollisionRadius(float radius);
}
