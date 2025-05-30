/*
 * Original work Copyright (C) 2023 Ocelot
 * Original code licensed under MIT License
 *
 * Modified by 17Artist on 2025-3-29
 * Modifications and redistribution licensed under GNU Lesser General Public License v3.0
 *
 * Changes:
 * - Renamed package from 'gg.moonflower.pinwheel.*' to 'priv.seventeen.artist.*' (all subpackages)
 * - Changed license from MIT to LGPL v3.0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package gg.moonflower.pinwheel.particle;


import gg.moonflower.molangcompiler.api.MolangExpression;
import gg.moonflower.pinwheel.particle.event.ParticleEvent;

import java.util.Random;

/**
 * Basic context from a particle for {@link ParticleEvent}.
 *
 * @author Ocelot
 * @since 1.0.0
 */
public interface ParticleContext {

    /**
     * Spawns a particle effect.
     *
     * @param effect The effect to spawn
     * @param type   The way to spawn the particle
     */
    void particleEffect(String effect, ParticleEvent.ParticleSpawnType type);

    /**
     * Plays a sound.
     *
     * @param sound The id of the sound to play
     */
    void soundEffect(String sound);

    /**
     * Executes an expression.
     *
     * @param expression The expression to execute
     */
    void expression(MolangExpression expression);

    /**
     * Logs a message to chat.
     *
     * @param message The message to send
     */
    void log(String message);

    /**
     * @return The source of randomness for particle events
     */
    Random getRandom();
}
