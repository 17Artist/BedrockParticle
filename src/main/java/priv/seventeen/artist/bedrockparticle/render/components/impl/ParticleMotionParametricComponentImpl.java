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
import gg.moonflower.molangcompiler.api.MolangExpression;
import gg.moonflower.pinwheel.particle.component.ParticleMotionParametricComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysicsComponent;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3dc;

@ApiStatus.Internal
public class ParticleMotionParametricComponentImpl extends BedrockParticleComponentImpl implements BedrockParticlePhysicsComponent {

    private final ParticleMotionParametricComponent data;

    public ParticleMotionParametricComponentImpl(BedrockParticle particle, ParticleMotionParametricComponent data) {
        super(particle);
        this.data = data;
    }

    @Override
    public void physicsTick() {
        MolangEnvironment environment = this.particle.getEnvironment();
        Vector3dc emitterPos = this.particle.getEmitter().position();

        MolangExpression[] relativePos = this.data.relativePosition();
        if (relativePos != null) {
            double x = emitterPos.x() + environment.safeResolve(relativePos[0]);
            double y = emitterPos.y() + environment.safeResolve(relativePos[1]);
            double z = emitterPos.z() + environment.safeResolve(relativePos[2]);
            this.particle.setPosition(x, y, z);
        }

        MolangExpression[] direction = this.data.direction();
        if (direction != null) {
            double dx = environment.safeResolve(direction[0]);
            double dy = environment.safeResolve(direction[1]);
            double dz = environment.safeResolve(direction[2]);
            this.getPhysics().setDirection(dx, dy, dz);
        }

        this.particle.setRoll(environment.safeResolve(this.data.rotation()));
    }
}
