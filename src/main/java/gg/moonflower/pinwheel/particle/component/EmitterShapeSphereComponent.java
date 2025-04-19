/*
 * Original work Copyright (C) 2023 Ocelot
 * Original code licensed under MIT License
 *
 * Modified by 17Artist on 2025-3-29
 * Modifications and redistribution licensed under GNU Lesser General Public License v3.0
 *
 * Changes:
 * - Renamed package from 'gg.moonflower.pinwheel.*' to 'priv.seventeen.artist.*' (all subpackages)
 * - Changed license from MIT to LGPL v3.0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package gg.moonflower.pinwheel.particle.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import gg.moonflower.molangcompiler.api.MolangEnvironment;
import gg.moonflower.molangcompiler.api.MolangExpression;
import gg.moonflower.pinwheel.particle.json.JsonTupleParser;
import gg.moonflower.pinwheel.particle.ParticleInstance;
import gg.moonflower.pinwheel.particle.json.PinwheelGsonHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * Component that spawns particles in a sphere.
 *
 * @author Ocelot
 * @since 1.0.0
 */
public record EmitterShapeSphereComponent(MolangExpression[] offset,
                                          MolangExpression radius,
                                          boolean surfaceOnly,
                                          @Nullable MolangExpression[] direction,
                                          boolean inwards) implements ParticleEmitterShape {

    public static EmitterShapeSphereComponent deserialize(JsonElement json) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        MolangExpression[] offset = JsonTupleParser.getExpression(jsonObject, "offset", 3, () -> new MolangExpression[]{MolangExpression.ZERO, MolangExpression.ZERO, MolangExpression.ZERO});
        MolangExpression radius = JsonTupleParser.getExpression(jsonObject, "radius", () -> MolangExpression.of(1));
        boolean surfaceOnly = PinwheelGsonHelper.getAsBoolean(jsonObject, "surface_only", false);
        Either<Boolean, MolangExpression[]> dir = ParticleComponent.parseDirection(jsonObject, "direction");
        MolangExpression[] direction = dir.right().orElse(null);
        boolean inwards = dir.left().orElse(false);
        return new EmitterShapeSphereComponent(offset, radius, surfaceOnly, direction, inwards);
    }

    @Override
    public void emitParticles(ParticleEmitterShape.Spawner spawner, int count) {
        Random random = spawner.getRandom();
        for (int i = 0; i < count; i++) {
            ParticleInstance particle = spawner.createParticle();
            MolangEnvironment environment = particle.getEnvironment();

            float offsetX = environment.safeResolve(this.offset[0]);
            float offsetY = environment.safeResolve(this.offset[1]);
            float offsetZ = environment.safeResolve(this.offset[2]);
            float radius = environment.safeResolve(this.radius);
            float r = this.surfaceOnly ? radius : (float) (radius * Math.sqrt(random.nextFloat()));

            float x = random.nextFloat() * 2 - 1;
            float y = random.nextFloat() * 2 - 1;
            float z = random.nextFloat() * 2 - 1;
            float length = r / (x * x + y * y + z * z);
            x *= length;
            y *= length;
            z *= length;

            float dx;
            float dy;
            float dz;
            if (this.direction != null) {
                dx = environment.safeResolve(this.direction[0]);
                dy = environment.safeResolve(this.direction[1]);
                dz = environment.safeResolve(this.direction[2]);
            } else {
                dx = x;
                dy = y;
                dz = z;
                if (this.inwards) {
                    dx = -dx;
                    dy = -dy;
                    dz = -dz;
                }
            }

            spawner.setPositionVelocity(particle, offsetX + x, offsetY + y, offsetZ + z, dx, dy, dz);
            spawner.spawnParticle(particle);
        }
    }
}
