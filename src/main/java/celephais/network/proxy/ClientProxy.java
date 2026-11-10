package celephais.network.proxy;

import celephais.util.EffectManager;
import celephais.util.LibRegistry;
import celephais.util.particle.ParticleRenderer;
import celephais.util.fx.FXHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
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
        MinecraftForge.EVENT_BUS.post(new RegisterParticleEvent());
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);

    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
}