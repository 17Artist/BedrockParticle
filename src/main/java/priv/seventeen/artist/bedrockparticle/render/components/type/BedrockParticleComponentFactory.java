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

import com.google.gson.JsonParseException;
import gg.moonflower.pinwheel.particle.component.ParticleComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleComponent;
import gg.moonflower.pollen.particle.BedrockParticleEmitter;
import gg.moonflower.pollen.particle.BedrockParticleManager;

/**
 * A component that can be added to a custom particle or particle emitter. Use {@link #particle(BedrockParticleComponentFactory)} and {@link #emitter(BedrockParticleComponentFactory)} to specify specific components.
 *
 * @param <T> The type of particle component to create
 * @author Ocelot
 * @see BedrockParticleManager
 * @since 2.0.0
 */
@FunctionalInterface
public interface BedrockParticleComponentFactory<T extends ParticleComponent> {

    /**
     * Creates a new particle component for the specified particle.
     *
     * @param data The data to parse the component from
     * @return The created component
     * @throws JsonParseException If there is an error parsing the component
     */
    BedrockParticleComponent create(BedrockParticle particle, T data) throws JsonParseException;

    /**
     * Whether this component can be used by the specified particle.
     *
     * @param particle The particle to check
     * @return Whether it is valid
     */
    default boolean isValid(BedrockParticle particle) {
        return true;
    }

    /**
     * Creates a particle type for particles specifically.
     *
     * @param type The type to wrap
     * @param <T>  The type of particle component to create
     * @return A type that only supports particles
     */
    static <T extends ParticleComponent> BedrockParticleComponentFactory<T> particle(BedrockParticleComponentFactory<T> type) {
        return new BedrockParticleComponentFactory<T>() {
            @Override
            public BedrockParticleComponent create(BedrockParticle particle, T data) throws JsonParseException {
                return type.create(particle, data);
            }

            @Override
            public boolean isValid(BedrockParticle particle) {
                return !(particle instanceof BedrockParticleEmitter);
            }
        };
    }

    /**
     * Creates a particle type for emitters specifically.
     *
     * @param type The type to wrap
     * @param <T>  The type of particle component to create
     * @return A type that only supports emitters
     */
    static <T extends ParticleComponent> BedrockParticleComponentFactory<T> emitter(BedrockParticleComponentFactory<T> type) {
        return new BedrockParticleComponentFactory<T>() {
            @Override
            public BedrockParticleComponent create(BedrockParticle particle, T data) throws JsonParseException {
                return type.create(particle, data);
            }

            @Override
            public boolean isValid(BedrockParticle particle) {
                return particle instanceof BedrockParticleEmitter;
            }
        };
    }
}
