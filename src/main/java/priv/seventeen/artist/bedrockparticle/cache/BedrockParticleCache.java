/*
 * Original work Copyright (C) 2023 GeckoLib
 * Original code licensed under MIT License
 *
 * Modified by 17Artist on 2025-3-29
 * Modifications and redistribution licensed under GNU Lesser General Public License v3.0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package priv.seventeen.artist.bedrockparticle.cache;

import gg.moonflower.pollen.particle.BedrockParticleManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @program: bedrockparticle
 * @description: 缓存
 * @author: 17Artist
 * @create: 2025-03-25 16:16
 **/
public class BedrockParticleCache {
    public static void registerReloadListener() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.getResourceManager() instanceof ReloadableResourceManager resourceManager)
            resourceManager.registerReloadListener(BedrockParticleCache::reload);

    }

    private static CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier stage, ResourceManager resourceManager,
                                                  ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor,
                                                  Executor gameExecutor) {
        Map<ResourceLocation, String> particles = new HashMap<>();

        return CompletableFuture.allOf(
                        load(backgroundExecutor, resourceManager, particles::put)
                .thenCompose(stage::wait).thenAcceptAsync(empty -> {
                    BedrockParticleManager.loadParticle(particles);
                }, gameExecutor));
    }

    private static CompletableFuture<Void> load(Executor backgroundExecutor, ResourceManager resourceManager,
                                                          BiConsumer<ResourceLocation, String> elementConsumer) {
        return loadResources(backgroundExecutor, resourceManager, "bedrockparticle", resource -> {
            try {
                return getFileContents(resource, resourceManager);
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }, elementConsumer);
    }

    public static String getFileContents(ResourceLocation location, ResourceManager manager) {
        try (InputStream inputStream = manager.getResourceOrThrow(location).open()) {
            return IOUtils.toString(inputStream, Charset.defaultCharset());
        }
        catch (Exception e) {
            throw new RuntimeException(new FileNotFoundException(location.toString()));
        }
    }

    private static <T> CompletableFuture<Void> loadResources(Executor executor, ResourceManager resourceManager,
                                                             String path, Function<ResourceLocation, T> loader, BiConsumer<ResourceLocation, T> map) {
        return CompletableFuture.supplyAsync(
                        () -> resourceManager.listResources(path, fileName -> fileName.toString().endsWith(".json")), executor)
                .thenApplyAsync(resources -> {
                    Map<ResourceLocation, CompletableFuture<T>> tasks = new Object2ObjectOpenHashMap<>();

                    for (ResourceLocation resource : resources.keySet()) {
                        tasks.put(resource, CompletableFuture.supplyAsync(() -> loader.apply(resource), executor));
                    }

                    return tasks;
                }, executor)
                .thenAcceptAsync(tasks -> {
                    for (Map.Entry<ResourceLocation, CompletableFuture<T>> entry : tasks.entrySet()) {
                        map.accept(entry.getKey(), entry.getValue().join());
                    }
                }, executor);
    }
}
