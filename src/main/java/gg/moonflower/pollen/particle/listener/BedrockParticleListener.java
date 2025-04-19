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

package gg.moonflower.pollen.particle.listener;

import gg.moonflower.pollen.particle.BedrockParticle;

/**
 * Listens for lifecycle events in {@link BedrockParticle}.
 *
 * @author Ocelot
 * @since 1.6.0
 */
public interface BedrockParticleListener {

    /**
     * Called when the specified particle is created.
     *
     * @param particle The added particle
     */
    default void onCreate(BedrockParticle particle) {
    }

    /**
     * Called when the specified particle is removed.
     *
     * @param particle The removed particle
     */
    default void onExpire(BedrockParticle particle) {
    }

    /**
     * Called when the particle collides with a block.
     *
     * @param particle The particle colliding
     * @param x        If the collision was in the x axis
     * @param y        If the collision was in the y axis
     * @param z        If the collision was in the z axis
     */
    default void onCollide(BedrockParticle particle, boolean x, boolean y, boolean z) {
    }

    /**
     * Called when the particle moves.
     *
     * @param particle The particle to move
     * @param dx       The offset in the x
     * @param dy       The offset in the y
     * @param dz       The offset in the z
     */
    default void onMove(BedrockParticle particle, double dx, double dy, double dz) {
    }
}
