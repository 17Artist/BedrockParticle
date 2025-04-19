/*
 * Copyright (C) 2023 Ocelot
 * Licensed under GNU Lesser General Public License v3.0
 *
 * Minor modifications by 17Artist (2025-3-29)
 *
 * Changes:
 * - Renamed package from ‘gg.moonflower.pollen.*’  to 'priv.seventeen.artist' (all subpackages)
 * - Added properties for relative rotation and relative position
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package gg.moonflower.pollen.particle;

import gg.moonflower.pollen.particle.listener.BedrockParticleEmitterListener;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface BedrockParticleEmitter extends BedrockParticle {


    void setRelativePosition(boolean relativePosition);

    boolean isRelativePosition();

    void setRelativeRotation(boolean relativeRotation);

    boolean isRelativeRotation();

    /**
     * Adds the specified listener to the emitter list.
     *
     * @param listener The listener to add
     */
    void addEmitterListener(BedrockParticleEmitterListener listener);

    /**
     * Removes the specified listener from the emitter list.
     *
     * @param listener The listener to remove
     */
    void removeEmitterListener(BedrockParticleEmitterListener listener);

    /**
     * Spawns the specified number of particles.
     *
     * @param count The amount of particles to spawn
     */
    void emitParticles(int count);

    /**
     * Attempts to restart the emitter if possible. This causes a "loop".
     */
    void restart();

    /**
     * @return Creates a new particle that can be summoned with {@link #summonParticle(BedrockParticle, double, double, double)}
     */
    BedrockParticle newParticle();

    /**
     * Summons a particle relative to this particle.
     *
     * @param particle The particle to add
     * @param x        The x position relative to this particle to summon
     * @param y        The y position relative to this particle to summon
     * @param z        The z position relative to this particle to summon
     */
    void summonParticle(BedrockParticle particle, double x, double y, double z);

    /**
     * Summons a particle relative to this particle.
     *
     * @param particle The particle to summon
     * @param x        The x position relative to this particle to summon
     * @param y        The y position relative to this particle to summon
     * @param z        The z position relative to this particle to summon
     * @param dx       The motion in the x
     * @param dy       The motion in the y
     * @param dz       The motion in the z
     */
    void summonParticle(ParticleOptions particle, double x, double y, double z, double dx, double dy, double dz);

    /**
     * @return The amount of time to spawn particles for
     */
    default float getActiveTime() {
        return this.getParticleLifetime();
    }

    /**
     * @return Whether particles are currently being spawned
     */
    default boolean isActive() {
        return this.getActiveTime() > this.getParticleAge();
    }

    /**
     * @return The number of particles spawned
     */
    int getSpawnedParticles();

    /**
     * @return The entity this emitter is attached to or <code>null</code> if freestanding
     */
    @Nullable
    Entity getEntity();

}
