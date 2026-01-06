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
import gg.moonflower.pollen.particle.listener.BedrockParticleEmitterListener;
import org.jetbrains.annotations.ApiStatus;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleTickComponent;

@ApiStatus.Internal
public class EmitterRateInstantComponentImpl extends BedrockParticleEmitterComponentImpl implements BedrockParticleTickComponent, BedrockParticleEmitterListener {

    private static final int MAX_SPAWN_PER_TICK = 30;

    private final EmitterRateInstantComponent data;
    private boolean complete;

    private int pendingCount;

    public EmitterRateInstantComponentImpl(BedrockParticle particle, EmitterRateInstantComponent data) {
        super(particle);
        this.data = data;
    }

    @Override
    public void tick() {
        if (this.complete) {
            return;
        }
        if (this.pendingCount <= 0) {
            this.pendingCount = (int) this.particle.getEnvironment().safeResolve(this.data.particleCount());
        }
        int spawnNow = Math.min(this.pendingCount, MAX_SPAWN_PER_TICK);
        if (spawnNow > 0) {
            this.particle.emitParticles(spawnNow);
            this.pendingCount -= spawnNow;
        }
        if (this.pendingCount <= 0) {
            this.complete = true;
        }
    }

    @Override
    public void onLoop(BedrockParticleEmitter emitter) {
        this.complete = false;
        this.pendingCount = 0;
    }
}
