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

import gg.moonflower.pinwheel.particle.component.ParticleLifetimeEventComponent;
import gg.moonflower.pollen.particle.BedrockParticle;
import priv.seventeen.artist.bedrockparticle.render.components.BedrockParticleTickComponent;
import gg.moonflower.pollen.particle.listener.BedrockParticleListener;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class BedrockParticleLifetimeEventComponentImpl implements BedrockParticleTickComponent, BedrockParticleListener {

    private final BedrockParticle particle;
    private final ParticleLifetimeEventComponent data;
    private int currentEvent;

    public BedrockParticleLifetimeEventComponentImpl(BedrockParticle particle, ParticleLifetimeEventComponent data) {
        this.particle = particle;
        this.data = data;
        this.currentEvent = 0;
    }

    @Override
    public void tick() {
        ParticleLifetimeEventComponent.TimelineEvent[] timelineEvents = this.data.timelineEvents();
        if (this.currentEvent >= timelineEvents.length) {
            return;
        }

        ParticleLifetimeEventComponent.TimelineEvent event = timelineEvents[this.currentEvent];
        float time = this.particle.getParticleAge();
        while (time >= event.time()) { // Execute all events that have been passed
            for (String e : event.events()) {
                this.particle.runEvent(e);
            }
            if (++this.currentEvent >= timelineEvents.length) {
                break;
            }
            event = timelineEvents[this.currentEvent];
        }
    }

    @Override
    public void onCreate(BedrockParticle particle) {
        for (String event : this.data.creationEvent()) {
            particle.runEvent(event);
        }
    }

    @Override
    public void onExpire(BedrockParticle particle) {
        for (String event : this.data.expirationEvent()) {
            particle.runEvent(event);
        }
    }
}
