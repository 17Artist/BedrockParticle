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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import priv.seventeen.artist.bedrockparticle.cache.BedrockParticleCache;
import priv.seventeen.artist.bedrockparticle.hook.arcartx.ArcartXHooker;

@Mod(BedrockParticle.MODID)
public class BedrockParticle {

    public static final String MODID = "bedrockparticle";

    public static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public BedrockParticle() {
        PinwheelMolangCompiler.set(MolangCompiler.create(MolangCompiler.OPTIMIZE_FLAG, BedrockParticle.class.getClassLoader()));
        BedrockParticleCache.registerReloadListener();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }



    private void commonSetup(final FMLCommonSetupEvent event) {

        if(ModList.get().isLoaded("arcartx")){
            LOGGER.info("ArcartX is loaded, enabling compatibility features.");
            ArcartXHooker.init();
        }
    }

}
