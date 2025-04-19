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
import gg.moonflower.pinwheel.particle.ParticleInstance;
import gg.moonflower.pinwheel.particle.ParticleSourceObject;
import gg.moonflower.pinwheel.particle.component.ParticleEmitterShape;
import gg.moonflower.pollen.particle.BedrockParticle;
import gg.moonflower.pollen.particle.BedrockParticleEmitter;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticlePhysics;
import gg.moonflower.pollen.particle.listener.BedrockParticleEmitterListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Random;

@ApiStatus.Internal
public class EmitterShapeComponentImpl extends BedrockParticleEmitterComponentImpl implements BedrockParticleEmitterListener, ParticleEmitterShape.Spawner {

    private final ParticleEmitterShape data;
    private final Source source;

    public EmitterShapeComponentImpl(BedrockParticle particle, ParticleEmitterShape data) {
        super(particle);
        this.data = data;
        this.source = new Source();
    }

    @Override
    public void onEmitParticles(BedrockParticleEmitter emitter, int count) {
        this.data.emitParticles(this, count);
    }

    @Override
    public BedrockParticle createParticle() {
        return this.particle.newParticle();
    }

    @Override
    public void spawnParticle(ParticleInstance instance) {
        if (!(instance instanceof BedrockParticle bedrockParticle)) {
            throw new AssertionError();
        }

        Vector3dc pos = bedrockParticle.position();
        this.particle.summonParticle(bedrockParticle, pos.x(), pos.y(), pos.z());
    }

    @Override
    public @Nullable ParticleSourceObject getEntity() {
        Entity entity = this.particle.getEntity();
        if (entity == null) {
            return null;
        }

        this.source.bounds = entity.getBoundingBox();
        return this.source;
    }

    @Override
    public MolangEnvironment getEnvironment() {
        return this.particle.getEnvironment();
    }

    @Override
    public Random getRandom() {
        return this.particle.getRandom();
    }

    @Override
    public void setPosition(ParticleInstance instance, double x, double y, double z) {
        if (!(instance instanceof BedrockParticle bedrockParticle)) {
            throw new AssertionError();
        }

        bedrockParticle.setPosition(x, y, z);
    }

    @Override
    public void setVelocity(ParticleInstance instance, double dx, double dy, double dz) {
        if (!(instance instanceof BedrockParticle bedrockParticle)) {
            throw new AssertionError();
        }

        BedrockParticlePhysics physics = bedrockParticle.getPhysics();
        if (physics == null) {
            return;
        }

        BedrockParticlePhysics emitterPhysics = this.particle.getPhysics();
        if (emitterPhysics != null) {
            Vector3dc velocity = emitterPhysics.getVelocity();
            dx += velocity.x();
            dy += velocity.y();
            dz += velocity.z();
        }

        physics.setVelocity(new Vector3d(dx, dy, dz));
    }

    private static class Source implements ParticleSourceObject {

        private AABB bounds;

        @Override
        public float getMinX() {
            return (float) this.bounds.minX;
        }

        @Override
        public float getMinY() {
            return (float) this.bounds.minY;
        }

        @Override
        public float getMinZ() {
            return (float) this.bounds.minZ;
        }

        @Override
        public float getMaxX() {
            return (float) this.bounds.maxX;
        }

        @Override
        public float getMaxY() {
            return (float) this.bounds.maxY;
        }

        @Override
        public float getMaxZ() {
            return (float) this.bounds.maxZ;
        }
    }
}
