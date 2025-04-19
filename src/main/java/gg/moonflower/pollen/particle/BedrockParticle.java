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

package gg.moonflower.pollen.particle;

import gg.moonflower.molangcompiler.api.MolangExpression;
import gg.moonflower.pinwheel.particle.ParticleContext;
import gg.moonflower.pinwheel.particle.ParticleInstance;
import gg.moonflower.pollen.particle.render.QuadRenderProperties;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysics;
import gg.moonflower.pollen.particle.listener.BedrockParticleListener;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

/**
 * An instance of a bedrock modular particle
 *
 * @author Ocelot
 * @since 2.0.0
 */
public interface BedrockParticle extends ParticleInstance, ParticleContext {


    /**
     * Adds the specified listener to the listener list.
     *
     * @param listener The listener to add
     */
    void addListener(BedrockParticleListener listener);

    /**
     * Removes the specified listener from the listener list.
     *
     * @param listener The listener to remove
     */
    void removeListener(BedrockParticleListener listener);

    /**
     * Runs the specified event if it exists.
     *
     * @param name The event to run
     */
    void runEvent(String name);

    @Override
    default void expression(MolangExpression expression) {
        this.getEnvironment().safeResolve(expression);
    }

    /**
     * @return The position of this particle
     */
    Vector3dc position();

    /**
     * @return The position of this particle aligned to the block grid
     */
    BlockPos blockPosition();

    /**
     * @return The roll of this particle
     */
    float roll();

    /**
     * @return The render position of this particle
     */
    Vector3dc position(float partialTicks);

    /**
     * @return The roll of this particle
     */
    float roll(float partialTicks);

    /**
     * Removes this particle and triggers listeners.
     */
    void expire();

    /**
     * @return If this particle is scheduled to be removed
     */
    boolean isExpired();

    /**
     * @return The name of this particle
     */
    ResourceLocation getName();

    /**
     * @return The level this particle is in
     */
    Level getLevel();

    /**
     * Retrieves the light UV at this particle's position.
     *
     * @return The packed lightmap coordinates
     */
    default int getPackedLight() {
        Level level = this.getLevel();
        BlockPos blockPos = this.blockPosition();
        return level.hasChunkAt(blockPos) ? LevelRenderer.getLightColor(level, blockPos) : 0;
    }

    /**
     * @return The physics of this particle or <code>null</code> if there are none
     */
    @Nullable BedrockParticlePhysics getPhysics();

    /**
     * @return The properties to use when rendering or <code>null</code> if nothing is rendered
     */
    @Nullable QuadRenderProperties getRenderProperties();

    /**
     * @return The emitter source this particle came from, or this particle if already an emitter
     */
    BedrockParticleEmitter getEmitter();



    /**
     * Sets the amount of time an emitter should spawn particles for or how long a particle should live.
     *
     * @param time The duration to spawn in seconds
     */
    void setLifetime(float time);

    /**
     * Sets whether particles will be spawned.
     *
     * @param active If particles should spawn
     */
    default void setActive(boolean active) {
        this.setLifetime(active ? Float.MAX_VALUE : 0);
    }

    /**
     * Sets the x position of this particle.
     *
     * @param x The new x value
     */
    void setX(double x);

    /**
     * Sets the y position of this particle.
     *
     * @param y The new y value
     */
    void setY(double y);

    /**
     * Sets the z position of this particle.
     *
     * @param z The new z value
     */
    void setZ(double z);

    /**
     * Sets the position of this particle.
     *
     * @param x The new x value
     * @param y The new y value
     * @param z The new z value
     */
    void setPosition(double x, double y, double z);

    /**
     * Sets the position of this particle.
     *
     * @param pos The new position
     */
    default void setPosition(Vector3dc pos) {
        this.setPosition(pos.x(), pos.y(), pos.z());
    }

    /**
     * Sets the roll of this particle.
     *
     * @param roll The new roll value
     */
    void setRoll(float roll);

    /**
     * Sets the render properties for this particle. Only works if this is a particle.
     *
     * @param properties The new properties to use when rendering or <code>null</code> to draw nothing
     */
    void setRenderProperties(@Nullable QuadRenderProperties properties);
}
