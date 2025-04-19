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

package priv.seventeen.artist.bedrockparticle.render.components.type;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import gg.moonflower.pinwheel.particle.component.ParticleComponent;
import gg.moonflower.pollen.particle.BedrockParticleManager;

/**
 * Deserializes particle data components from JSON.
 *
 * @param <T> The type of particle component to create
 * @author Ocelot
 * @see BedrockParticleManager
 * @since 2.0.0
 */
@FunctionalInterface
public interface BedrockParticleDataFactory<T extends ParticleComponent> {

    /**
     * Creates a new particle component for the specified particle.
     *
     * @param data The data to parse the component from
     * @return The created component
     * @throws JsonParseException If there is an error parsing the component
     */
    T create(JsonElement data) throws JsonParseException;
}
