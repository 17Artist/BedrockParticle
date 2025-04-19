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

import gg.moonflower.pinwheel.particle.component.EmitterLifetimeOnceComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleTickComponent;
import gg.moonflower.pollen.particle.listener.BedrockParticleListener;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class EmitterLifetimeOnceComponentImpl extends BedrockParticleEmitterComponentImpl implements BedrockParticleTickComponent, BedrockParticleListener {

    private final EmitterLifetimeOnceComponent data;
    private int activeTimeEval;

    public EmitterLifetimeOnceComponentImpl(BedrockParticle particle, EmitterLifetimeOnceComponent data) {
        super(particle);
        this.data = data;
    }

    @Override
    public void tick() {
        this.particle.setLifetime(this.activeTimeEval);
        if (!this.particle.isActive()) {
            this.particle.expire();
        }
    }

    @Override
    public void onCreate(BedrockParticle particle) {
        this.activeTimeEval = (int) particle.getEnvironment().safeResolve(this.data.activeTime());
    }
}
