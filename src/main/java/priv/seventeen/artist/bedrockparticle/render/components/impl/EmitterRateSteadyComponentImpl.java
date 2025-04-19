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

import gg.moonflower.pinwheel.particle.component.EmitterRateSteadyComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import gg.moonflower.pollen.particle.BedrockParticleEmitter;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleTickComponent;
import gg.moonflower.pollen.particle.listener.BedrockParticleEmitterListener;
import gg.moonflower.pollen.particle.listener.BedrockParticleListener;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class EmitterRateSteadyComponentImpl extends BedrockParticleEmitterComponentImpl implements BedrockParticleTickComponent, BedrockParticleListener, BedrockParticleEmitterListener {

    private final EmitterRateSteadyComponent data;
    private int maxParticlesEval;

    public EmitterRateSteadyComponentImpl(BedrockParticle particle, EmitterRateSteadyComponent data) {
        super(particle);
        this.data = data;
    }

    private int evaluateSpawnCount() {
        return (int) (this.particle.getEnvironment().safeResolve(this.data.spawnRate()) / 20F);
    }

    @Override
    public void tick() {
        int maxCount = this.maxParticlesEval - this.particle.getSpawnedParticles();
        if (maxCount <= 0) {
            return;
        }

        int spawnCount = this.evaluateSpawnCount();
        for (int i = 0; i < Math.min(spawnCount, maxCount); i++) {
            this.particle.emitParticles(1);
            spawnCount = this.evaluateSpawnCount();
        }
    }

    @Override
    public void onCreate(BedrockParticle particle) {
        this.maxParticlesEval = (int) this.particle.getEnvironment().safeResolve(this.data.maxParticles());
    }

    @Override
    public void onLoop(BedrockParticleEmitter emitter) {
        this.onCreate(emitter);
    }
}
