/*
 * Copyright (C) 2023 Ocelot
 * Licensed under GNU Lesser General Public License v3.0
 *
 * Minor modifications by 17Artist (2025-3-29)
 *
 * Changes:
 * - Renamed package from ‘gg.moonflower.pollen.*’  to 'priv.seventeen.artist' (all subpackages)
 * - Merged all interfaces and implementation classes into a single entity class
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package gg.moonflower.pollen.particle.render;

import gg.moonflower.molangcompiler.api.MolangEnvironment;
import gg.moonflower.pinwheel.particle.render.Flipbook;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

/**
 * @program: bedrockparticle
 * @description: QuadRenderProperties
 * @author: 17Artist
 * @create: 2025-03-30 00:32
 **/
@Getter
public class QuadRenderProperties {

    @Setter
    private float red = 1F;

    @Setter
    private float green = 1F;

    @Setter
    private float blue = 1F;

    @Setter
    private float alpha = 1F;

    private final Quaternionf rotation = new Quaternionf();

    @Setter
    private float width = 0F;

    @Setter
    private float height = 0F;

    private float uMin = 0F;

    private float vMin = 0F;

    private float uMax = 0F;

    private float vMax = 0F;

    @Setter
    private boolean direction = false;

    @Setter
    private int packedLight = LightTexture.FULL_BRIGHT;



    public void setRotation(Quaternionfc rotation){
        this.rotation.set(rotation);
    }


    public void setColor(float red, float green, float blue, float alpha) {
        this.setRed(red);
        this.setGreen(green);
        this.setBlue(blue);
        this.setAlpha(alpha);
    }

    public void setColor(int color) {
        this.setRed((float) (color >> 16 & 0xFF) / 255F);
        this.setGreen((float) (color >> 8 & 0xFF) / 255F);
        this.setBlue((float) (color & 0xFF) / 255F);
        this.setAlpha((float) (color >> 24 & 0xFF) / 255F);
    }

    public boolean canRender() {
        return this.width * this.height > 0;
    }

    public void setUV(float uMin, float vMin, float uMax, float vMax) {
        this.uMin = uMin;
        this.vMin = vMin;
        this.uMax = uMax;
        this.vMax = vMax;
    }

    public void setUV(MolangEnvironment environment, int textureWidth, int textureHeight, Flipbook flipbook, float time, float maxLife) {
        int maxFrame = (int) environment.safeResolve(flipbook.maxFrame());
        int frame;
        if (flipbook.stretchToLifetime()) {
            frame = Math.min((int) (time / maxLife * (maxFrame + 1)), maxFrame);
        } else {
            frame = (int) (time * flipbook.fps());
            if (flipbook.loop()) {
                frame %= maxFrame;
            } else {
                frame = Math.min(frame, maxFrame);
            }
        }

        float u = environment.safeResolve(flipbook.baseU());
        float v = environment.safeResolve(flipbook.baseV());
        float uSize = flipbook.sizeU();
        float vSize = flipbook.sizeV();
        float uo = flipbook.stepU() * frame;
        float vo = flipbook.stepV() * frame;

        float uMin = (u + uo) / (float) textureWidth;
        float vMin = (v + vo) / (float) textureHeight;
        float uMax = (u + uo + uSize) / (float) textureWidth;
        float vMax = (v + vo + vSize) / (float) textureHeight;
        this.setUV(uMin, vMin, uMax, vMax);
    }

}
