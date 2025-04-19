/*
 * Copyright (C) 2023 Ocelot
 * Licensed under GNU Lesser General Public License v3.0
 *
 * Minor modifications by 17Artist (2025-3-29)
 *
 * Changes:
 * - Renamed package from ‘gg.moonflower.pollen.*’  to 'priv.seventeen.artist' (all subpackages)
 * - Eliminated/Removed the original registry implementation
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package priv.seventeen.artist.bedrockparticle.render.components.type;

import gg.moonflower.pinwheel.particle.component.*;
import gg.moonflower.pinwheel.particle.component.ParticleAppearanceBillboardComponent;
import gg.moonflower.pinwheel.particle.component.ParticleAppearanceLightingComponent;
import gg.moonflower.pinwheel.particle.component.ParticleAppearanceTintingComponent;
import gg.moonflower.pinwheel.particle.component.ParticleExpireInBlocksComponent;
import gg.moonflower.pinwheel.particle.component.ParticleExpireNotInBlocksComponent;
import gg.moonflower.pinwheel.particle.component.ParticleKillPlaneComponent;
import gg.moonflower.pinwheel.particle.component.ParticleLifetimeEventComponent;
import gg.moonflower.pinwheel.particle.component.EmitterLifetimeExpressionComponent;
import gg.moonflower.pinwheel.particle.component.EmitterLifetimeLoopingComponent;
import gg.moonflower.pinwheel.particle.component.EmitterLifetimeOnceComponent;
import gg.moonflower.pinwheel.particle.component.ParticleLifetimeExpressionComponent;
import gg.moonflower.pinwheel.particle.component.EmitterInitializationComponent;
import gg.moonflower.pinwheel.particle.component.EmitterLocalSpaceComponent;
import gg.moonflower.pinwheel.particle.component.EmitterRateInstantComponent;
import gg.moonflower.pinwheel.particle.component.EmitterRateSteadyComponent;
import net.minecraft.resources.ResourceLocation;
import priv.seventeen.artist.bedrockparticle.render.components.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Bedrock particles components that can be added using JSON.</p>
 * Components use a static data class for loading from JSON, then another component class that actually implements the behavior.
 * View some of the built-in components to understand how to implement more components.</p>
 *
 * @author Ocelot
 * @since 2.0.0
 */

public class BedrockParticleComponents {

    public static final Map<ResourceLocation, BedrockParticleComponentType<?>> COMPONENT_TYPE = new HashMap<>();

    static {
        register("emitter_lifetime_events", ParticleLifetimeEventComponent::deserialize, BedrockParticleComponentFactory.emitter(BedrockParticleLifetimeEventComponentImpl::new));
        register("emitter_lifetime_expression", EmitterLifetimeExpressionComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterLifetimeExpressionComponentImpl::new));
        register("emitter_lifetime_looping", EmitterLifetimeLoopingComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterLifetimeLoopingComponentImpl::new));
        register("emitter_lifetime_once", EmitterLifetimeOnceComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterLifetimeOnceComponentImpl::new));
        register("emitter_rate_instant", EmitterRateInstantComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterRateInstantComponentImpl::new));
        register("emitter_rate_steady", EmitterRateSteadyComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterRateSteadyComponentImpl::new));
        register("emitter_shape_disc", EmitterShapeDiscComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterShapeComponentImpl::new));
        register("emitter_shape_box", EmitterShapeBoxComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterShapeComponentImpl::new));
        register("emitter_shape_custom", EmitterShapePointComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterShapeComponentImpl::new));
        register("emitter_shape_entity_aabb", EmitterShapeEntityBoxComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterShapeComponentImpl::new));
        register("emitter_shape_point", EmitterShapePointComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterShapeComponentImpl::new));
        register("emitter_shape_sphere", EmitterShapeSphereComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterShapeComponentImpl::new));

        register("emitter_initialization", EmitterInitializationComponent::deserialize, BedrockParticleComponentFactory.emitter(ParticleInitializationComponentImpl::new));
        register("particle_initialization", EmitterInitializationComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleInitializationComponentImpl::new));
        register("emitter_local_space", EmitterLocalSpaceComponent::deserialize, BedrockParticleComponentFactory.emitter(EmitterLocalSpaceComponentImpl::new));

        register("particle_appearance_billboard", ParticleAppearanceBillboardComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleAppearanceBillboardComponentImpl::new));
        register("particle_appearance_lighting", ParticleAppearanceLightingComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleAppearanceLightingComponentImpl::new));
        register("particle_appearance_tinting", ParticleAppearanceTintingComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleAppearanceTintingComponentImpl::new));

        register("particle_initial_speed", ParticleInitialSpeedComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleInitialSpeedComponentImpl::new));
        register("particle_initial_spin", ParticleInitialSpinComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleInitialSpinComponentImpl::new));

        register("particle_expire_if_in_blocks",ParticleExpireInBlocksComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleExpireInBlocksComponentImpl::new));
        register("particle_expire_if_not_in_blocks", ParticleExpireNotInBlocksComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleExpireNotInBlocksComponentImpl::new));
        register("particle_lifetime_events", ParticleLifetimeEventComponent::deserialize, BedrockParticleComponentFactory.particle(BedrockParticleLifetimeEventComponentImpl::new));
        register("particle_lifetime_expression", ParticleLifetimeExpressionComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleLifetimeExpressionComponentImpl::new));
        register("particle_kill_plane", ParticleKillPlaneComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleKillPlaneComponentImpl::new));

        register("particle_motion_collision", ParticleMotionCollisionComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleMotionCollisionComponentImpl::new));
        register("particle_motion_dynamic", ParticleMotionDynamicComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleMotionDynamicComponentImpl::new));
        register("particle_motion_parametric", ParticleMotionParametricComponent::deserialize, BedrockParticleComponentFactory.particle(ParticleMotionParametricComponentImpl::new));

    }






    static <T extends ParticleComponent> BedrockParticleComponentType<T> register(String key, BedrockParticleDataFactory<T> dataFactory, BedrockParticleComponentFactory<T> componentFactory) {
        BedrockParticleComponentType<T> type = new BedrockParticleComponentType<>(dataFactory, componentFactory);
        COMPONENT_TYPE.put(ResourceLocation.parse(key), type);
        return type;
    }
}
