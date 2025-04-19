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

import gg.moonflower.pinwheel.particle.component.EmitterRateInstantComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import gg.moonflower.pollen.particle.BedrockParticleEmitter;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleTickComponent;
import gg.moonflower.pollen.particle.listener.BedrockParticleEmitterListener;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class EmitterRateInstantComponentImpl extends BedrockParticleEmitterComponentImpl implements BedrockParticleTickComponent, BedrockParticleEmitterListener {

    private final EmitterRateInstantComponent data;
    private boolean complete;

    public EmitterRateInstantComponentImpl(BedrockParticle particle, EmitterRateInstantComponent data) {
        super(particle);
        this.data = data;
    }

    @Override
    public void tick() {
        if (!this.complete) {
            int count = (int) this.particle.getEnvironment().safeResolve(this.data.particleCount());
            this.particle.emitParticles(count);
            this.complete = true;
        }
    }

    @Override
    public void onLoop(BedrockParticleEmitter emitter) {
        this.complete = false;
    }
}
