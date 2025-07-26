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
package priv.seventeen.artist.bedrockparticle.hook.arcartx.data;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import priv.seventeen.artist.arcartx.common.api.anmiation.locator.GeoLocatorInfo;
import priv.seventeen.artist.arcartx.common.api.events.EventHandler;
import priv.seventeen.artist.arcartx.common.api.events.Listener;
import priv.seventeen.artist.arcartx.common.api.events.arcartx.animation.AnimationEffectEvent;
import priv.seventeen.artist.arcartx.common.api.events.arcartx.animation.AnimationLocatorUpdateEvent;
import priv.seventeen.artist.arcartx.common.api.events.minecraft.ClientTickEvent;
import priv.seventeen.artist.arcartx.common.api.events.minecraft.world.WorldChangeEvent;
import priv.seventeen.artist.bedrockparticle.hook.arcartx.target.ArcartXTarget;
import priv.seventeen.artist.bedrockparticle.hook.arcartx.target.BodyTarget;
import priv.seventeen.artist.bedrockparticle.hook.arcartx.target.LookTarget;
import priv.seventeen.artist.bedrockparticle.hook.arcartx.target.WorldTarget;
import priv.seventeen.artist.bedrockparticle.render.particle.instance.BedrockParticleEmitterImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @program: bedrockparticle
 * @description: AX实体数据管理
 * @author: 17Artist
 * @create: 2025-03-31 06:40
 **/
public class ArcartXEntityManager implements Listener {

    static Map<UUID, ArcartXEntityData> effectDataMap = new HashMap<>();


    public static ArcartXEntityData getOrCreateEffectData(Entity entity) {
        return effectDataMap.computeIfAbsent(entity.getUUID(), k -> new ArcartXEntityData(entity));
    }


    @EventHandler
    public void onUpdate(AnimationLocatorUpdateEvent event){
        ArcartXEntityData data = getOrCreateEffectData((Entity) event.entity());
        data.update(event.locatorInfos());
    }



    @EventHandler
    public void onTick(ClientTickEvent event)
    {
        if(event.start()){
            if(Minecraft.getInstance().level == null){
                effectDataMap.clear();
                return;
            }
            effectDataMap.entrySet().removeIf(entry -> entry.getValue().checkRemove());
        }
    }

    @EventHandler
    public void onWorldChange(WorldChangeEvent event){
        effectDataMap.clear();
    }

    @EventHandler
    public void onAnimationEffectEvent(AnimationEffectEvent event){
        if(event.namespace().equalsIgnoreCase("bedrock") && event.type().equalsIgnoreCase("particle") && event.targetLocators().length == 1){

            String id = event.args().getOrDefault("id","");
            String mode = event.args().getOrDefault("mode","look");
            // look | body | world
            double yaw;
            double pitch;
            try{
                yaw = Double.parseDouble(event.args().getOrDefault("yaw","0"));
                pitch = Double.parseDouble(event.args().getOrDefault("pitch","0"));
            } catch (Exception e){
                yaw = 0;
                pitch = 0;
            }
            String locator = event.targetLocators()[0];
            if(locator.isEmpty()){
                locator = "entity_position";
            }
            ArcartXEntityData data = ArcartXEntityManager.getOrCreateEffectData((Entity) event.entity());
            GeoLocatorInfo geoLocatorInfo = data.getLocator(locator);
            ArcartXTarget target = switch (mode){
                case "body" -> new BodyTarget(data,locator,yaw);
                case "world" -> new WorldTarget(data,locator,yaw,pitch);
                default -> new LookTarget(data,locator,yaw,pitch);
            };
            BedrockParticleEmitterImpl emitter =
                    new BedrockParticleEmitterImpl(
                            (Entity) event.entity(),
                            target,
                            Minecraft.getInstance().level,
                            geoLocatorInfo.x(), geoLocatorInfo.y(), geoLocatorInfo.z(),
                            ResourceLocation.tryParse(id));
            emitter.pitch = target.getYaw(1);
            emitter.yaw = target.getPitch(1);

            Minecraft.getInstance().particleEngine.add(emitter);
        }
    }





}
