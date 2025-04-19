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

import gg.moonflower.molangcompiler.api.MolangEnvironment;
import gg.moonflower.pinwheel.particle.component.EmitterLifetimeExpressionComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleTickComponent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class EmitterLifetimeExpressionComponentImpl extends BedrockParticleEmitterComponentImpl implements BedrockParticleTickComponent {

    private final EmitterLifetimeExpressionComponent data;

    public EmitterLifetimeExpressionComponentImpl(BedrockParticle particle, EmitterLifetimeExpressionComponent data) {
        super(particle);
        this.data = data;
    }

    @Override
    public void tick() {
        MolangEnvironment environment = this.particle.getEnvironment();
        this.particle.setActive(environment.safeResolve(this.data.activation()) != 0);
        if (environment.safeResolve(this.data.expiration()) != 0) {
            this.particle.expire();
        }
    }
}
