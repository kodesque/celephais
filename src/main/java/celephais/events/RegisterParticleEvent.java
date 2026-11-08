package celephais.events;

import celephais.util.ParticleBase;
import celephais.util.ParticleRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RegisterParticleEvent extends Event {
    public RegisterParticleEvent() {
    }

    public void registerParticle(String modid, Class<? extends ParticleBase> particleClass, ResourceLocation texture) {
        ParticleRegistry.registerParticle(modid, particleClass, texture);
    }
}