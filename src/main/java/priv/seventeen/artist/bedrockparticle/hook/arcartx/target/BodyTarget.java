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
package priv.seventeen.artist.bedrockparticle.hook.arcartx.target;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import priv.seventeen.artist.bedrockparticle.hook.arcartx.data.ArcartXEntityData;

/**
 * @program: bedrockparticle
 * @description: 根据身体朝向旋转
 * @author: 17Artist
 * @create: 2025-04-01 08:32
 **/
public class BodyTarget extends ArcartXTarget{

    private final double yaw;

    public BodyTarget(ArcartXEntityData entityData, String locatorName, double yaw) {
        super(entityData, locatorName);
        this.yaw = yaw;
    }

    @Override
    public float getYaw(float partialTicks) {
        if(getEntityData().entity instanceof LivingEntity living){
            return Mth.wrapDegrees(living.yBodyRot + (float) this.yaw);
        }
        return Mth.wrapDegrees(this.getEntityData().entity.getViewYRot(partialTicks) + (float) this.yaw);
    }

    @Override
    public float getPitch(float partialTicks) {
        return 0;
    }
}
