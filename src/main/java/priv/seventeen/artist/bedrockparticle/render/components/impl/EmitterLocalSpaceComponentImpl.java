/*
 * Copyright (C) 2023 Ocelot
 * Licensed under GNU Lesser General Public License v3.0
 *
 * Minor modifications by 17Artist (2025-3-29)
 *
 * Changes:
 * - Renamed package from ‘gg.moonflower.pollen.*’  to 'priv.seventeen.artist' (all subpackages)
 * - Added implementation for relative position functionality
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package priv.seventeen.artist.bedrockparticle.render.components.impl;

import gg.moonflower.pinwheel.particle.component.EmitterLocalSpaceComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import gg.moonflower.pollen.particle.BedrockParticleEmitter;
import gg.moonflower.pollen.particle.listener.BedrockParticleEmitterListener;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class EmitterLocalSpaceComponentImpl extends BedrockParticleEmitterComponentImpl implements BedrockParticleEmitterListener {

    private final EmitterLocalSpaceComponent data;

    public EmitterLocalSpaceComponentImpl(BedrockParticle particle, EmitterLocalSpaceComponent data) {
        super(particle);
        this.data = data;
    }

    @Override
    public void onEmitParticles(BedrockParticleEmitter emitter, int count) {
        emitter.setRelativePosition(data.position());
        emitter.setRelativeRotation(data.rotation());
    }

}
