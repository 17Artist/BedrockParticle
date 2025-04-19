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
package priv.seventeen.artist.bedrockparticle.render.components.impl;

import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleComponent;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysics;
import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;

@ApiStatus.Internal
public abstract class BedrockParticleComponentImpl implements BedrockParticleComponent {

    protected final BedrockParticle particle;

    protected BedrockParticleComponentImpl(BedrockParticle particle) {
        this.particle = particle;
    }

    protected BedrockParticlePhysics getPhysics() {
        return Objects.requireNonNull(this.particle.getPhysics(), "physics");
    }
}
