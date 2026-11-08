package celephais.events;

import celephais.client.fx.EffectCut;
import celephais.client.fx.EffectManager;
import celephais.client.fx.FXRegistry;
import celephais.client.render.RenderNull;
import celephais.common.entity.EntitySlash;
import celephais.common.items.ItemCrissaegrim;
import celephais.network.proxy.ClientProxy;
import celephais.root.Main;
import celephais.util.LibRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Function;

public class ModRegistryHandler {
    private static int entityId = 0;
    public static Item crissaegrim;
    public static int FX_CUT;

    public ModRegistryHandler() {
    }

    @SubscribeEvent
    public void registerContent(RegisterContentEvent event) {
        LibRegistry.setActiveMod("celephais", Main.CONTAINER);
        event.addItem(crissaegrim = new ItemCrissaegrim("celephais"));
        LibRegistry.registerEntity(EntitySlash.class);
        if (Main.proxy instanceof ClientProxy) {
            LibRegistry.registerEntityRenderer(EntitySlash.class, new RenderNull.Factory());
        }

    }

    @SubscribeEvent
    public void registerFX(RegisterFXEvent event) {
        FX_CUT = FXRegistry.registerEffect(new Function<NBTTagCompound, Void>() {
            public Void apply(NBTTagCompound t) {
                EffectCut slash = new EffectCut(Minecraft.getMinecraft().world.provider.getDimension());
                slash.read(t);
                EffectManager.addEffect(slash);
                return null;
            }
        });
    }
}
