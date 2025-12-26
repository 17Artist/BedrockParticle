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
import gg.moonflower.molangcompiler.api.exception.MolangException;
import gg.moonflower.pinwheel.particle.PinwheelMolangCompiler;
import gg.moonflower.pinwheel.particle.json.JsonTupleParser;
import gg.moonflower.pinwheel.particle.json.PinwheelGsonHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Component that initializes emitters.
 *
 * @author Ocelot
 * @since 1.0.0
 */
public record EmitterInitializationComponent(MolangExpression[] creationExpressions,
                                             MolangExpression[] updateExpressions,
                                             MolangExpression[] renderExpressions) implements ParticleEmitterComponent {

    public static EmitterInitializationComponent deserialize(JsonElement json) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        return new EmitterInitializationComponent(
                parseExpressions(object, "creation_expression"),
                parseExpressions(object, "per_update_expression"),
                parseExpressions(object, "per_render_expressions"));
    }

    private static MolangExpression[] parseExpressions(JsonObject object, String name) throws JsonParseException {
        if (!object.has(name)) {
            return new MolangExpression[0];
        }

        JsonElement element = object.get(name);
        if (element.isJsonArray()) {
            List<MolangExpression> expressions = new ArrayList<>();
            for (JsonElement entry : element.getAsJsonArray()) {
                expressions.add(JsonTupleParser.parseExpression(entry, name));
            }
            return expressions.toArray(MolangExpression[]::new);
        }

        if (element.isJsonPrimitive()) {
            String raw = PinwheelGsonHelper.convertToString(element, name);
            String[] parts = raw.split(";");
            List<MolangExpression> expressions = new ArrayList<>(parts.length);
            for (String part : parts) {
                String trimmed = part.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                try {
                    expressions.add(PinwheelMolangCompiler.get().compile(trimmed));
                } catch (MolangException e) {
                    throw new JsonParseException("Failed to compile " + name, e);
                }
            }
            return expressions.toArray(MolangExpression[]::new);
        }

        throw new JsonParseException("Expected " + name + " to be a JsonArray or string");
    }

    @Override
    public boolean canLoop() {
        return true;
    }
}
