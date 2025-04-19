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

import gg.moonflower.pinwheel.particle.component.ParticleKillPlaneComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import gg.moonflower.pollen.particle.BedrockParticleEmitter;
import gg.moonflower.pollen.particle.listener.BedrockParticleListener;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3dc;

@ApiStatus.Internal
public class ParticleKillPlaneComponentImpl extends BedrockParticleComponentImpl implements BedrockParticleListener {

    private final ParticleKillPlaneComponent data;

    public ParticleKillPlaneComponentImpl(BedrockParticle particle, ParticleKillPlaneComponent data) {
        super(particle);
        this.data = data;
    }

    @Override
    public void onMove(BedrockParticle particle, double dx, double dy, double dz) {
        BedrockParticleEmitter emitter = particle.getEmitter();
        Vector3dc pos = particle.position();
        Vector3dc emitterPos = emitter.position();

        boolean result = this.data.solve(pos.x() - dx - emitterPos.x(), pos.y() - dy - emitterPos.y(), pos.z() - dz - emitterPos.z(), pos.x() - emitterPos.x(), pos.y() - emitterPos.y(), pos.z() - emitterPos.z());
        if (result) {
            particle.expire();
        }
    }
}
