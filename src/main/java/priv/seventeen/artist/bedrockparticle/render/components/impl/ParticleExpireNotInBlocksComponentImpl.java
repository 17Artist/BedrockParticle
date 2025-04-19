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

import com.mojang.logging.LogUtils;
import gg.moonflower.pinwheel.particle.component.ParticleExpireNotInBlocksComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleTickComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Objects;

@ApiStatus.Internal
public class ParticleExpireNotInBlocksComponentImpl extends BedrockParticleComponentImpl implements BedrockParticleTickComponent {

    private static final Logger LOGGER = LogUtils.getLogger();
    private final Block[] blocks;

    public ParticleExpireNotInBlocksComponentImpl(BedrockParticle particle, ParticleExpireNotInBlocksComponent data) {
        super(particle);
        this.blocks = Arrays.stream(data.blocks()).map(name -> {
            ResourceLocation id = ResourceLocation.tryParse(name);
            if (id == null) {
                LOGGER.error("Invalid block id: " + name);
                return null;
            }
            if (!BuiltInRegistries.BLOCK.containsKey(id)) {
                LOGGER.error("Unknown block: " + name);
                return null;
            }

            return  BuiltInRegistries.BLOCK.get(id);
        }).filter(Objects::nonNull).toArray(Block[]::new);
    }

    @Override
    public void tick() {
        Level level = this.particle.getLevel();
        Block block = level.getBlockState(this.particle.blockPosition()).getBlock();
        for (Block test : this.blocks) {
            if (block != test) {
                this.particle.expire();
                break;
            }
        }
    }
}
