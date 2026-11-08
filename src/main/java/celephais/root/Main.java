package celephais.root;

import celephais.events.ModEventHandler;
import celephais.events.ModRegistryHandler;
import celephais.network.proxy.CommonProxy;
import celephais.util.LibEvents;
import celephais.util.LibRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;;

@Mod(
        modid = Main.MODID,
        version = Main.VERSION,
        name = Main.MODNAME
//        acceptedMinecraftVersions = "[1.12.2]"
)
public class Main {
    public static final String MODID = "celephais";
    public static final String VERSION = "1.0.1";
    public static final String MODNAME = "Crissaegrim Aeternum";
    @SidedProxy(
            clientSide = "crissaegrim.network.proxy.ClientProxy",
            serverSide = "crissaegrim.network.proxy.CommonProxy"
    )
    public static CommonProxy proxy;
    @Mod.Instance
    public static Main INSTANCE;
    public static ModContainer CONTAINER;

    public Main() {
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CONTAINER = Loader.instance().activeModContainer();
        MinecraftForge.EVENT_BUS.register(new ModRegistryHandler());
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        proxy.preInit(event);

        MinecraftForge.EVENT_BUS.register(new LibRegistry());
        MinecraftForge.EVENT_BUS.register(new LibEvents());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
