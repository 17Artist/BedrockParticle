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

import gg.moonflower.pinwheel.particle.component.ParticleAppearanceLightingComponent;
import gg.moonflower.pollen.particle.render.QuadRenderProperties;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleRenderComponent;
import net.minecraft.client.Camera;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ParticleAppearanceLightingComponentImpl extends BedrockParticleComponentImpl implements BedrockParticleRenderComponent {

    public ParticleAppearanceLightingComponentImpl(BedrockParticle particle, ParticleAppearanceLightingComponent data) {
        super(particle);
    }

    @Override
    public void render(Camera camera, float partialTicks) {
        QuadRenderProperties properties = this.particle.getRenderProperties();
        if (properties != null) {
            properties.setPackedLight(this.particle.getPackedLight());
        }
    }
}
