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

import gg.moonflower.pollen.particle.BedrockParticleEmitter;

/**
 * Listens for special events in {@link BedrockParticleEmitter}.
 *
 * @author Ocelot
 * @since 2.0.0
 */
public interface BedrockParticleEmitterListener {

    /**
     * Spawns the specified number of particles.
     *
     * @param count The amount of particles spawned
     */
    default void onEmitParticles(BedrockParticleEmitter emitter, int count) {
    }

    /**
     * Called when the specified emitter restarts.
     *
     * @param emitter The emitter to loop
     */
    default void onLoop(BedrockParticleEmitter emitter) {
    }
}
