/*
 * Copyright (C) 2025 17Artist
 * Licensed under GNU Lesser General Public License v3.0
 *
 * This file is part of bedrockparticle.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package priv.seventeen.artist.bedrockparticle.render.rendertype;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;

/**
 * @program: bedrockparticle
 * @description: BedrockParticleRenderType
 * @author: 17Artist
 * @create: 2025-03-19 17:12
 **/
public abstract class BedrockParticleRenderType extends RenderType  {

    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_SHADER = new RenderStateShard.ShaderStateShard(GameRenderer::getParticleShader);
    private static final BiFunction<ResourceLocation, Boolean, RenderType> RENDERTYPE = Util.memoize((p_286156_, p_286157_) -> {
        RenderType.CompositeState rendertype$compositestate =
                RenderType.CompositeState.builder()
                        .setTextureState(new RenderStateShard.TextureStateShard(p_286156_, false, false))
                        .setShaderState(RENDERTYPE_SHADER)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setCullState(NO_CULL)
                        .setLightmapState(LIGHTMAP)
                        .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                        .createCompositeState(true);
        return create("bedrock_particle",
                DefaultVertexFormat.PARTICLE,
                VertexFormat.Mode.QUADS,
                256, true, true, rendertype$compositestate);
    });

    public BedrockParticleRenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }


    public static RenderType get(ResourceLocation p_110444_) {
        return RENDERTYPE.apply(p_110444_,true);
    }


}
