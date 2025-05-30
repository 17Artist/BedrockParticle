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

/**
 * @program: bedrockparticle
 * @description: 粒子跟随目标
 * @author: 17Artist
 * @create: 2025-03-29 02:53
 **/
public interface ParticleTarget {

    double getX(float partialTicks);

    double getY(float partialTicks);

    double getZ(float partialTicks);

    float getYaw(float partialTicks);

    float getPitch(float partialTicks);

}
