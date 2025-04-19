/*
 * Copyright (C) 2025 17Artist
 * Licensed under GNU Lesser General Public License v3.0
 *
 * This file is part of bedrockparticle.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package priv.seventeen.artist.bedrockparticle.target;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

/**
 * @program: bedrockparticle
 * @description: 实体跟随
 * @author: 17Artist
 * @create: 2025-03-30 04:06
 **/
public class EntityTarget implements ParticleTarget{

    private final Entity entity;

    public EntityTarget(Entity entity){
        this.entity = entity;
    }

    @Override
    public double getX(float partialTicks) {
        return entity.getPosition(partialTicks).x();
    }

    @Override
    public double getY(float partialTicks) {
        return entity.getPosition(partialTicks).y();
    }

    @Override
    public double getZ(float partialTicks) {
        return entity.getPosition(partialTicks).z();
    }

    @Override
    public float getYaw(float partialTicks) {
        return Mth.wrapDegrees(entity.getViewYRot(partialTicks));
    }

    @Override
    public float getPitch(float partialTicks) {
        return entity.getViewXRot(partialTicks);
    }
}
