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

import gg.moonflower.pinwheel.particle.ParticleData;
import priv.seventeen.artist.bedrockparticle.render.particle.BedrockParticleManagerImpl;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;


/**
 * Loads and manages all Bedrock particle definitions.
 *
 * @author Ocelot
 * @since 2.0.0
 */
public interface BedrockParticleManager {


    static void loadParticle(Map<ResourceLocation, String> particles) {
            BedrockParticleManagerImpl.loadParticle(particles);
    }


    /**
     * Fetches a particle by the specified name.
     *
     * @param location The name of the particle
     * @return The particle found or {@link ParticleData#EMPTY} if there was no particle
     */
    static ParticleData getParticle(ResourceLocation location) {
        return BedrockParticleManagerImpl.getParticle(location);
    }

    /**
     * Checks if a particle is registered under the specified name.
     *
     * @param location The name of the particle
     * @return Whether that particles exists
     */
    static boolean hasParticle(ResourceLocation location) {
        return BedrockParticleManagerImpl.hasParticle(location);
    }
}
