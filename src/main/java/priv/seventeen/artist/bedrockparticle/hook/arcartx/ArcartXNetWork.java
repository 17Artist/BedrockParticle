package priv.seventeen.artist.bedrockparticle.hook.arcartx;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import priv.seventeen.artist.arcartx.common.api.events.EventHandler;
import priv.seventeen.artist.arcartx.common.api.events.Listener;
import priv.seventeen.artist.arcartx.common.api.events.arcartx.client.CustomPacketEvent;
import priv.seventeen.artist.bedrockparticle.render.particle.instance.BedrockParticleEmitterImpl;
import priv.seventeen.artist.bedrockparticle.target.LocationTarget;

/**
 * @program: bedrockparticle
 * @description: 通讯
 * @author: 17Artist
 * @create: 2025-05-12 23:48
 **/
public class ArcartXNetWork implements Listener {

    @EventHandler
    public void onCustomPacketEvent(CustomPacketEvent event){
        if(!event.getId().equalsIgnoreCase("bedrockparticle") || event.getArgSize() != 6){
            return;
        }
        String id = event.getArg(0);
        double x = event.getArgAsDouble(1);
        double y = event.getArgAsDouble(2);
        double z = event.getArgAsDouble(3);
        float yaw = event.getArgAsFloat(4);
        float pitch = event.getArgAsFloat(5);

        LocationTarget target = new LocationTarget(x, y, z, yaw, pitch);

        BedrockParticleEmitterImpl emitter = new BedrockParticleEmitterImpl(null, target, Minecraft.getInstance().level, x, y, z, ResourceLocation.tryParse(id));
        emitter.pitch = target.getYaw(1);
        emitter.yaw = target.getPitch(1);
        Minecraft.getInstance().particleEngine.add(emitter);
    }

}
