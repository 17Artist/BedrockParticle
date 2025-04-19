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

import net.minecraft.world.entity.Entity;
import priv.seventeen.artist.arcartx.common.api.anmiation.locator.GeoLocatorInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: bedrockparticle
 * @description: AX实体数据
 * @author: 17Artist
 * @create: 2025-03-31 06:40
 **/
public class ArcartXEntityData {

    static GeoLocatorInfo DEFAULT_LOCATOR = new GeoLocatorInfo("default",0,0,0);

    Map<String, GeoLocatorInfo> locators = new HashMap<>();

    public final Entity entity;

    ArcartXEntityData(Entity entity){
        this.entity = entity;
    }

    public boolean checkRemove(){
        return entity.isRemoved();
    }

    public void update(Collection<GeoLocatorInfo> geoLocatorInfo){
        locators.clear();
        geoLocatorInfo.forEach(info -> locators.put(info.name(),info));
    }

    public GeoLocatorInfo getLocator(String name){
        return locators.getOrDefault(name,DEFAULT_LOCATOR);
    }




}
