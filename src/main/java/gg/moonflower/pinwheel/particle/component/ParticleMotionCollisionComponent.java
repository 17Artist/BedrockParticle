/*
 * Original work Copyright (C) 2023 Ocelot
 * Original code licensed under MIT License
 *
 * Modified by 17Artist on 2025-3-29
 * Modifications and redistribution licensed under GNU Lesser General Public License v3.0
 *
 * Changes:
 * - Renamed package from 'gg.moonflower.pinwheel.*' to 'priv.seventeen.artist.*' (all subpackages)
 * - Changed license from MIT to LGPL v3.0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package gg.moonflower.pinwheel.particle.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import gg.moonflower.molangcompiler.api.MolangExpression;
import gg.moonflower.pinwheel.particle.json.JsonTupleParser;
import gg.moonflower.pinwheel.particle.json.PinwheelGsonHelper;

/**
 * Component that specifies how a particle moves after colliding.
 *
 * @author Ocelot
 * @since 1.0.0
 */
public record ParticleMotionCollisionComponent(MolangExpression enabled,
                                               float collisionDrag,
                                               float coefficientOfRestitution,
                                               float collisionRadius,
                                               boolean expireOnContact,
                                               String[] events) implements ParticleComponent {

    public static final MolangExpression DEFAULT_ENABLED = MolangExpression.of(true);

    public static ParticleMotionCollisionComponent deserialize(JsonElement json) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        MolangExpression enabled = JsonTupleParser.getExpression(jsonObject, "enabled", () -> DEFAULT_ENABLED);
        float collisionDrag = PinwheelGsonHelper.getAsFloat(jsonObject, "collision_drag", 0) / 20F;
        float coefficientOfRestitution = PinwheelGsonHelper.getAsFloat(jsonObject, "coefficient_of_restitution", 0);
        float collisionRadius = PinwheelGsonHelper.getAsFloat(jsonObject, "collision_radius", 0.1F);
        boolean expireOnContact = PinwheelGsonHelper.getAsBoolean(jsonObject, "expire_on_contact", false);
        String[] events = ParticleComponent.getEvents(jsonObject, "events");
        return new ParticleMotionCollisionComponent(enabled, collisionDrag, coefficientOfRestitution, collisionRadius, expireOnContact, events);
    }
}
