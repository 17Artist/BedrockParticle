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

import gg.moonflower.pinwheel.particle.ParticleData;
import gg.moonflower.pinwheel.particle.ParticleParser;
import gg.moonflower.pollen.particle.BedrockParticleManager;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import priv.seventeen.artist.bedrockparticle.BedrockParticle;

import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
public final class BedrockParticleManagerImpl {

    private static final Logger LOGGER = LogManager.getLogger(BedrockParticleManager.class);
    private static final Map<ResourceLocation, ParticleData> PARTICLES = new HashMap<>();

    public static ParticleData getParticle(ResourceLocation location) {
        return PARTICLES.computeIfAbsent(location, key -> {
            LOGGER.warn("Unknown particle with key '{}'", location);
            return ParticleData.EMPTY;
        });
    }

    public static boolean hasParticle(ResourceLocation location) {
        return PARTICLES.containsKey(location) && PARTICLES.get(location) != ParticleData.EMPTY;
    }

    public static void addParticle(ResourceLocation location, ParticleData particle) {
        PARTICLES.put(location, particle);
    }

    public static void loadParticle(Map<ResourceLocation, String> particles) {
        PARTICLES.clear();
        particles.forEach((k,v)->{
            try {
                ParticleData particle = ParticleParser.parseParticle(v);
                PARTICLES.put(k, particle);
                BedrockParticle.LOGGER.info("[Loaded particle] >>>>> {}:{}", k.getNamespace(), k.getPath());
            } catch (Exception e){
                BedrockParticle.LOGGER.error("[Error loading particle] >>>>> {}:{}", k.getNamespace(), k.getPath(), e);
            }
        });
    }




}
