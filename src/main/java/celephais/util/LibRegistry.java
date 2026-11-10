package celephais.util;


import celephais.client.particles.ParticleGlitter;
import celephais.client.particles.ParticleGlow;
import celephais.client.particles.ParticleSpark;
import celephais.root.Main;
import celephais.util.particle.ParticleRegistry;
import epicsquid.mysticallib.event.RegisterContentEvent;
import epicsquid.mysticallib.event.RegisterCustomModelsEvent;
import epicsquid.mysticallib.event.RegisterParticleEvent;
import epicsquid.mysticallib.model.BakedModelColorWrapper;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import epicsquid.mysticallib.model.block.BakedModelBlockUnlitWrapper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LibRegistry {
    private static ArrayList<Item> items = new ArrayList();
    private static ArrayList<Block> blocks = new ArrayList();
    private static Map<Class<? extends Entity>, IRenderFactory> entityRenderMap = new HashMap();
    private static Map<Class<? extends TileEntity>, TileEntitySpecialRenderer> tileEntityRenderMap = new HashMap();
    private static int entityId = 0;
    private static String activeModid = "";
    private static Set<ModelResourceLocation> noCullMRLs = new HashSet();
    private static Set<ModelResourceLocation> colorMRLs = new HashSet();
    public static String PARTICLE_GLOW;
    public static String PARTICLE_SMOKE;
    public static String PARTICLE_SPARK;
    public static String PARTICLE_GLITTER;
    public static String PARTICLE_FLAME;

    public LibRegistry() {
    }

    public static String getActiveModid() {
        return activeModid;
    }

    public static void initAll() {
        ModContainer container = Loader.instance().activeModContainer();
        MinecraftForge.EVENT_BUS.post(new RegisterContentEvent(items, blocks));
        setActiveMod("celephais", container);
    }

    @SideOnly(Side.CLIENT)
    public static void registerEntityRenderer(@Nonnull Class<? extends Entity> entity, @Nonnull IRenderFactory render) {
        entityRenderMap.put(entity, render);
    }

    public static void registerEntity(Class<? extends Entity> entity, int eggColor1, int eggColor2) {
        String[] nameParts = entity.getTypeName().split("\\.");
        String className = nameParts[nameParts.length - 1];
        EntityRegistry.registerModEntity(new ResourceLocation(activeModid + ":" + className.toLowerCase()), entity, className.toLowerCase(), entityId++, Main.INSTANCE, 64, 1, true, eggColor1, eggColor2);
    }

    public static void registerEntity(Class<? extends Entity> entity) {
        String[] nameParts = entity.getTypeName().split("\\.");
        String className = nameParts[nameParts.length - 1];
        EntityRegistry.registerModEntity(new ResourceLocation(activeModid + ":" + className.toLowerCase()), entity, className.toLowerCase(), entityId++, Main.INSTANCE, 64, 1, true);
    }

    public static void setActiveMod(@Nonnull String modid, @Nonnull ModContainer container) {
        activeModid = modid;
        Loader.instance().setActiveModContainer(container);
    }

    @SubscribeEvent
    public void registerItems(@Nonnull RegistryEvent.Register<Item> event) {
        for(Item i : items) {
            event.getRegistry().register(i);
        }

        for(Block b : blocks) {
                Item i = Item.getItemFromBlock(b);
                if (i != null) {
                    event.getRegistry().register(i);
                }
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerRendering(@Nonnull ModelRegistryEvent event) {
        for(Item i : items) {
            if (i instanceof IModeledObject) {
                ((IModeledObject)i).initModel();
            }
        }

        for(Block b : blocks) {
            if (b instanceof IModeledObject) {
                ((IModeledObject)b).initModel();
            }
        }

    }

    public static void registerEntityRenders() {
        for(Map.Entry<Class<? extends Entity>, IRenderFactory> e : entityRenderMap.entrySet()) {
            RenderingRegistry.registerEntityRenderingHandler((Class)e.getKey(), (IRenderFactory)e.getValue());
        }

        for(Map.Entry<Class<? extends TileEntity>, TileEntitySpecialRenderer> e : tileEntityRenderMap.entrySet()) {
            ClientRegistry.bindTileEntitySpecialRenderer((Class)e.getKey(), (TileEntitySpecialRenderer)e.getValue());
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRegisterCustomModels(@Nonnull RegisterCustomModelsEvent event) {
        for(Block b : blocks) {
            if (b instanceof ICustomModeledObject) {
                ((ICustomModeledObject)b).initCustomModel();
            }
        }

        for(Item i : items) {
            if (i instanceof ICustomModeledObject) {
                ((ICustomModeledObject)i).initCustomModel();
            }
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(@Nonnull TextureStitchEvent event) {
        for(Map.Entry<String, ResourceLocation> e : ParticleRegistry.particleTextures.entrySet()) {
            event.getMap().registerSprite((ResourceLocation)e.getValue());
        }

        for(Map.Entry<ResourceLocation, IModel> e : CustomModelLoader.itemmodels.entrySet()) {
            for(ResourceLocation r : ((IModel)e.getValue()).getTextures()) {
                event.getMap().registerSprite(r);
            }
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onModelBake(@Nonnull ModelBakeEvent event) {
        MinecraftForge.EVENT_BUS.post(new RegisterTintedModelsEvent());
        noCullMRLs.clear();

        for(ResourceLocation r : CustomModelLoader.itemmodels.keySet()) {
            ModelResourceLocation mrl = new ModelResourceLocation(r.toString().replace("#handlers", ""), "handlers");
            IBakedModel bakedModel = (IBakedModel)event.getModelRegistry().getObject(mrl);
            if (bakedModel != null) {
                IModel m = (IModel) CustomModelLoader.itemmodels.get(r);
                event.getModelRegistry().putObject(mrl, m.bake(m.getDefaultState(), DefaultVertexFormats.ITEM, ModelLoader.defaultTextureGetter()));
            }
        }

        for(ModelResourceLocation mrl : event.getModelRegistry().getKeys()) {
            if (noCullMRLs.contains(mrl)) {
                event.getModelRegistry().putObject(mrl, new BakedModelBlockUnlitWrapper(event.getModelManager().getModel(mrl)));
            }

            if (colorMRLs.contains(mrl)) {
                event.getModelRegistry().putObject(mrl, new BakedModelColorWrapper(event.getModelManager().getModel(mrl)));
            }
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRegisterCustomModels(@Nonnull RegisterParticleEvent event) {
        PARTICLE_GLOW = ParticleRegistry.registerParticle("celephais", ParticleGlow.class, new ResourceLocation("celephais:particle/particle_glow"));
        PARTICLE_SPARK = ParticleRegistry.registerParticle("celephais", ParticleSpark.class, new ResourceLocation("celephais:particle/particle_sparkle"));
        PARTICLE_GLITTER = ParticleRegistry.registerParticle("celephais", ParticleGlitter.class, new ResourceLocation("celephais:particle/particle_sparkle"));
    }

    public class RegisterTintedModelsEvent extends Event {
        public RegisterTintedModelsEvent() {
        }

        public void addModel(ModelResourceLocation mrl) {
            colorMRLs.add(mrl);
        }
    }
}