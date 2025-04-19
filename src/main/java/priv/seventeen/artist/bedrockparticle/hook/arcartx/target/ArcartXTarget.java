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

import lombok.Getter;
import lombok.Setter;
import priv.seventeen.artist.bedrockparticle.hook.arcartx.data.ArcartXEntityData;
import priv.seventeen.artist.bedrockparticle.target.ParticleTarget;

/**
 * @program: bedrockparticle
 * @description: ArcartX定位器兼容
 * @author: 17Artist
 * @create: 2025-03-31 06:32
 **/
@Setter @Getter
public abstract class ArcartXTarget implements ParticleTarget {


    private ArcartXEntityData entityData;


    private String locatorName;


    public ArcartXTarget(ArcartXEntityData entityData, String locatorName) {
        this.entityData = entityData;
        this.locatorName = locatorName;
    }

    @Override
    public double getX(float partialTicks) {
        return entityData.getLocator(locatorName).x();
    }

    @Override
    public double getY(float partialTicks) {
        return entityData.getLocator(locatorName).y();
    }

    @Override
    public double getZ(float partialTicks) {
        return entityData.getLocator(locatorName).z();
    }

}
