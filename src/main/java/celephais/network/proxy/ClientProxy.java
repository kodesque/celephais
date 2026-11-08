package celephais.network.proxy;

import celephais.client.fx.EffectManager;
import celephais.client.fx.FXHandler;
import celephais.events.RegisterColorHandlersEvent;
import celephais.events.RegisterParticleEvent;
import celephais.util.CustomModelLoader;
import celephais.util.Hax;
import celephais.util.LibRegistry;
import celephais.util.ParticleRenderer;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    public static ParticleRenderer particleRenderer = new ParticleRenderer();

    public ClientProxy() {
    }

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        MinecraftForge.EVENT_BUS.register(new FXHandler());

//        try {
//            Hax.init();
//        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//            ((Exception)e).printStackTrace();
//        }

        LibRegistry.registerEntityRenders();
        MinecraftForge.EVENT_BUS.register(new EffectManager());
        ModelLoaderRegistry.registerLoader(new CustomModelLoader());
        MinecraftForge.EVENT_BUS.post(new RegisterParticleEvent());
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);

        MinecraftForge.EVENT_BUS.post(new RegisterColorHandlersEvent());
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}