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
package priv.seventeen.artist.bedrockparticle.render.particle;

import gg.moonflower.molangcompiler.api.MolangEnvironment;
import gg.moonflower.molangcompiler.api.MolangEnvironmentBuilder;
import gg.moonflower.molangcompiler.api.MolangExpression;
import gg.moonflower.molangcompiler.api.exception.MolangRuntimeException;
import gg.moonflower.molangcompiler.api.object.MolangObject;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;
import java.util.function.Supplier;

@ApiStatus.Internal
public class ProfilingMolangEnvironment implements MolangEnvironment {

    private final MolangEnvironment environment;
    private final Supplier<ProfilerFiller> profiler;

    public ProfilingMolangEnvironment(MolangEnvironment environment, Supplier<ProfilerFiller> profiler) {
        this.environment = environment;
        this.profiler = profiler;
    }

    @Override
    public void loadLibrary(String name, MolangObject object, String... aliases) {
        this.environment.loadLibrary(name, object, aliases);
    }

    @Override
    public void loadAlias(String name, String first, String... aliases) throws IllegalArgumentException {
        this.environment.loadAlias(name, first, aliases);
    }

    @Override
    public void loadParameter(float value) throws MolangRuntimeException {
        this.environment.loadParameter(value);
    }

    @Override
    public void clearParameters() {
        this.environment.clearParameters();
    }

    @Override
    public float getThis() {
        return this.environment.getThis();
    }

    @Override
    public MolangObject get(String name) throws MolangRuntimeException {
        ProfilerFiller profiler = this.profiler.get();
        profiler.push("molang");
        MolangObject value = this.environment.get(name);
        profiler.pop();
        return value;
    }

    @Override
    public float getParameter(int parameter) throws MolangRuntimeException {
        return this.environment.getParameter(parameter);
    }

    @Override
    public int getParameters() {
        return this.environment.getParameters();
    }

    @Override
    public Collection<String> getObjects() {
        return this.environment.getObjects();
    }

    @Override
    public void setThisValue(float thisValue) {
        this.environment.setThisValue(thisValue);
    }

    @Override
    public boolean canEdit() {
        return this.environment.canEdit();
    }

    @Override
    public MolangEnvironmentBuilder<MolangEnvironment> edit() throws IllegalStateException {
        return new ProfilingEnvironmentBuilder(this, this.environment.edit());
    }

    private record ProfilingEnvironmentBuilder(MolangEnvironment environment,
                                               MolangEnvironmentBuilder<? extends MolangEnvironment> builder) implements MolangEnvironmentBuilder<MolangEnvironment> {

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> loadLibrary(String name, MolangObject object) {
            this.builder.loadLibrary(name, object);
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> unloadLibrary(String name) {
            this.builder.unloadLibrary(name);
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> setQuery(String name, MolangExpression value) {
            this.builder.setQuery(name, value);
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> setGlobal(String name, MolangExpression value) {
            this.builder.setGlobal(name, value);
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> setVariable(String name, MolangExpression value) {
            this.builder.setVariable(name, value);
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> removeQuery(String name) {
            this.builder.removeQuery(name);
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> removeGlobal(String name) {
            this.builder.removeGlobal(name);
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> removeVariable(String name) {
            this.builder.removeVariable(name);
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> clearLibraries() {
            this.builder.clearLibraries();
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> clearQuery() {
            this.builder.clearQuery();
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> clearGlobal() {
            this.builder.clearGlobal();
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> clearVariable() {
            this.builder.clearVariable();
            return this;
        }

        @Override
        public MolangEnvironmentBuilder<MolangEnvironment> copy(MolangEnvironment environment) {
            this.builder.copy(environment);
            return this;
        }

        @Override
        public MolangEnvironment create() {
            return this.environment;
        }
    }
}
