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
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import priv.seventeen.artist.bedrockparticle.cache.BedrockParticleCache;
import priv.seventeen.artist.bedrockparticle.hook.arcartx.ArcartXHooker;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;

import java.util.Locale;

@Mod(BedrockParticle.MODID)
public class BedrockParticle {

    public static final String MODID = "bedrockparticle";

    public static final Logger LOGGER = LogUtils.getLogger();


    public BedrockParticle(IEventBus modEventBus, ModContainer modContainer) {
        MolangCompiler compiler = MolangCompiler.create(MolangCompiler.OPTIMIZE_FLAG, BedrockParticle.class.getClassLoader());
        PinwheelMolangCompiler.set(input -> compiler.compile(normalizeMolang(input)));
        BedrockParticleCache.registerReloadListener();
        modEventBus.addListener(this::commonSetup);
    }



    private void commonSetup(final FMLCommonSetupEvent event) {

        if(ModList.get().isLoaded("arcartx")){
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

