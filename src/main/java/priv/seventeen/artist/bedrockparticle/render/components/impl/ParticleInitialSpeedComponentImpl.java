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
import gg.moonflower.pinwheel.particle.component.ParticleInitialSpeedComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysics;
import gg.moonflower.pollen.particle.listener.BedrockParticleListener;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3d;

@ApiStatus.Internal
public class ParticleInitialSpeedComponentImpl extends BedrockParticleComponentImpl implements BedrockParticleListener {

    private final ParticleInitialSpeedComponent data;

    public ParticleInitialSpeedComponentImpl(BedrockParticle particle, ParticleInitialSpeedComponent data) {
        super(particle);
        this.data = data;
    }

    @Override
    public void onCreate(BedrockParticle particle) {
        BedrockParticlePhysics physics = this.getPhysics();
        MolangEnvironment environment = particle.getEnvironment();
        float dx = environment.safeResolve(this.data.speed()[0]) / 20F;
        float dy = environment.safeResolve(this.data.speed()[1]) / 20F;
        float dz = environment.safeResolve(this.data.speed()[2]) / 20F;
        physics.setVelocity(physics.getDirection().mul(dx, dy, dz, new Vector3d()));
    }
}
