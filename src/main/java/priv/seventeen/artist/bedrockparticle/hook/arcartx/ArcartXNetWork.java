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

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import priv.seventeen.artist.arcartx.common.api.events.EventHandler;
import priv.seventeen.artist.arcartx.common.api.events.Listener;
import priv.seventeen.artist.arcartx.common.api.events.arcartx.client.CustomPacketEvent;
import priv.seventeen.artist.bedrockparticle.render.particle.instance.BedrockParticleEmitterImpl;
import priv.seventeen.artist.bedrockparticle.target.LocationTarget;

/**
 * @program: bedrockparticle
 * @description: 通讯
 * @author: 17Artist
 * @create: 2025-05-12 23:48
 **/
public class ArcartXNetWork implements Listener {

    @EventHandler
    public void onCustomPacketEvent(CustomPacketEvent event){
        if(!event.getId().equalsIgnoreCase("bedrockparticle") || event.getArgSize() != 6){
            return;
        }
        String id = event.getArg(0);
        double x = event.getArgAsDouble(1);
        double y = event.getArgAsDouble(2);
        double z = event.getArgAsDouble(3);
        float yaw = event.getArgAsFloat(4);
        float pitch = event.getArgAsFloat(5);

        LocationTarget target = new LocationTarget(x, y, z, yaw, pitch);

        BedrockParticleEmitterImpl emitter = new BedrockParticleEmitterImpl(null, target, Minecraft.getInstance().level, x, y, z, ResourceLocation.tryParse(id));
        emitter.pitch = target.getYaw(1);
        emitter.yaw = target.getPitch(1);
        Minecraft.getInstance().particleEngine.add(emitter);
    }

}
