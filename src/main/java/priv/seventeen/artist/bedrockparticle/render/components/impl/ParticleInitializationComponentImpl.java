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

import gg.moonflower.pinwheel.particle.component.EmitterInitializationComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleComponent;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleTickComponent;
import gg.moonflower.pollen.particle.listener.BedrockParticleListener;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ParticleInitializationComponentImpl implements BedrockParticleComponent, BedrockParticleTickComponent, BedrockParticleListener {

    private final BedrockParticle particle;
    private final EmitterInitializationComponent data;

    public ParticleInitializationComponentImpl(BedrockParticle particle, EmitterInitializationComponent data) {
        this.particle = particle;
        this.data = data;
    }

    @Override
    public void tick() {
        if (this.data.tickExpression() != null) {
            this.particle.getEnvironment().safeResolve(this.data.tickExpression());
        }
    }

    @Override
    public void onCreate(BedrockParticle particle) {
        if (this.data.creationExpression() != null) {
            this.particle.getEnvironment().safeResolve(this.data.creationExpression());
        }
    }
}
