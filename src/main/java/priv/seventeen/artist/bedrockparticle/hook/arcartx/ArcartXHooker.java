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
package priv.seventeen.artist.bedrockparticle.hook.arcartx;

import priv.seventeen.artist.arcartx.common.api.events.ArcartXEventManager;
import priv.seventeen.artist.bedrockparticle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.hook.arcartx.data.ArcartXEntityManager;

/**
 * @program: bedrockparticle
 * @description: ArcartX挂钩
 * @author: 17Artist
 * @create: 2025-03-31 07:17
 **/
public class ArcartXHooker {

    public static void init() {
//        if (!isClientClassesAvailable()) {
//            BedrockParticle.LOGGER.warn("ArcartX hook disabled: client classes are not available.");
//            return;
//        }
        ArcartXEventManager.registerListener(new ArcartXEntityManager());
        ArcartXEventManager.registerListener(new ArcartXNetWork());
    }

//    private static boolean isClientClassesAvailable() {
//        try {
//            Class.forName("net.minecraft.client.particle.Particle", false, ArcartXHooker.class.getClassLoader());
//            return true;
//        } catch (Throwable ignored) {
//            return false;
//        }
//    }

}
