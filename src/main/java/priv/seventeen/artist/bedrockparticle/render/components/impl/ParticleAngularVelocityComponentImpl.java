/*
 * Copyright (C) 2023 Ocelot
 * Licensed under GNU Lesser General Public License v3.0
 *
 * Minor modifications by 17Artist (2025-3-29)
 *
 * Changes:
 * - Renamed package from тАШgg.moonflower.pollen.*тА? to 'priv.seventeen.artist' (all subpackages)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package priv.seventeen.artist.bedrockparticle.render.components.impl;

import gg.moonflower.molangcompiler.api.MolangEnvironment;
import gg.moonflower.pinwheel.particle.component.ParticleAngularVelocityComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import gg.moonflower.pollen.particle.listener.BedrockParticleListener;
import org.jetbrains.annotations.ApiStatus;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysics;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysicsComponent;

@ApiStatus.Internal
public class ParticleAngularVelocityComponentImpl extends BedrockParticleComponentImpl implements BedrockParticleListener, BedrockParticlePhysicsComponent {

    private final ParticleAngularVelocityComponent data;

    public ParticleAngularVelocityComponentImpl(BedrockParticle particle, ParticleAngularVelocityComponent data) {
        super(particle);
        this.data = data;
    }

    @Override
    public void onCreate(BedrockParticle particle) {
        BedrockParticlePhysics physics = this.getPhysics();
        MolangEnvironment environment = particle.getEnvironment();
        float rollVelocity = (float) environment.safeResolve(this.data.angularVelocity()[2]) / 20F;
        physics.setRollVeclocity(rollVelocity);
    }

    @Override
    public void physicsTick() {
    }
}
