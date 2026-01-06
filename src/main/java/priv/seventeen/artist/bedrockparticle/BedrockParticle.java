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
package priv.seventeen.artist.bedrockparticle;

import com.mojang.logging.LogUtils;
import gg.moonflower.molangcompiler.api.MolangCompiler;
import gg.moonflower.pinwheel.particle.PinwheelMolangCompiler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import priv.seventeen.artist.bedrockparticle.hook.arcartx.ArcartXHooker;

import java.util.Locale;

public class BedrockParticle implements ClientModInitializer {

    public static final Logger LOGGER = LogUtils.getLogger();



    @Override
    public void onInitializeClient() {
        MolangCompiler compiler = MolangCompiler.create(MolangCompiler.OPTIMIZE_FLAG, BedrockParticle.class.getClassLoader());
        PinwheelMolangCompiler.set(input -> compiler.compile(normalizeMolang(input)));
        if(FabricLoader.getInstance().isModLoaded("arcartx")){
            LOGGER.info("ArcartX is loaded, enabling compatibility features.");
            ArcartXHooker.init();
        }
    }

    private static String normalizeMolang(String input) {
        if (input == null) {
            return null;
        }
        return input.toLowerCase(Locale.ROOT);
    }
}
