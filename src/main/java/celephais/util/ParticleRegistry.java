package celephais.util;

import celephais.network.proxy.ClientProxy;
import celephais.root.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ParticleRegistry {
    public static Map<String, ResourceLocation> particleTextures = new HashMap();
    private static Map<String, Constructor<? extends ParticleBase>> particles = new HashMap();

    public ParticleRegistry() {
    }

    public static String registerParticle(@Nonnull String modid, @Nonnull Class<? extends ParticleBase> particleClass, @Nonnull ResourceLocation texture) {
        String name = Util.getLowercaseClassName(particleClass);
        if (Main.proxy instanceof ClientProxy) {
            try {
                if (!particles.containsKey(name) && !particleTextures.containsKey(name)) {
                    particles.put(name, particleClass.getConstructor(World.class, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, double[].class));
                    particleTextures.put(name, texture);
                } else {
                    System.out.println("WARNING: PARTICLE ALREADY REGISTERED WITH NAME \"" + name + "\"!");
                }
            } catch (SecurityException | NoSuchMethodException e) {
                ((Exception)e).printStackTrace();
            }
        }

        return name;
    }

    public static Map<String, Constructor<? extends ParticleBase>> getParticles() {
        return particles;
    }
}