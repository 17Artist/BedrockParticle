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

import gg.moonflower.molangcompiler.api.MolangEnvironment;
import gg.moonflower.pinwheel.particle.component.ParticleAppearanceTintingComponent;
import gg.moonflower.pollen.particle.render.QuadRenderProperties;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleRenderComponent;
import net.minecraft.client.Camera;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ParticleAppearanceTintingComponentImpl extends BedrockParticleComponentImpl implements BedrockParticleRenderComponent {

    private final ParticleAppearanceTintingComponent data;

    public ParticleAppearanceTintingComponentImpl(BedrockParticle particle, ParticleAppearanceTintingComponent data) {
        super(particle);
        this.data = data;
    }

    @Override
    public void render(Camera camera, float partialTicks) {
        QuadRenderProperties properties = this.particle.getRenderProperties();
        if (properties != null) {
            MolangEnvironment environment = this.particle.getEnvironment();
            properties.setColor(this.data.red().get(this.particle, environment), this.data.green().get(this.particle, environment), this.data.blue().get(this.particle, environment), this.data.alpha().get(this.particle, environment));
        }
    }
}
