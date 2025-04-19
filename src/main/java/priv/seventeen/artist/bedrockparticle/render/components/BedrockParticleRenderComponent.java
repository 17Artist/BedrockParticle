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

package priv.seventeen.artist.bedrockparticle.render.components;

import net.minecraft.client.Camera;

/**
 * A component for controlling particle render properties.
 *
 * @author Ocelot
 * @since 2.0.0
 */
public interface BedrockParticleRenderComponent {

    /**
     * Called before the particle is rendered to set up properties.
     *
     * @param camera       The camera rendering the particle
     * @param partialTicks The percentage from last tick to this tick
     */
    void render(Camera camera, float partialTicks);
}
