package celephais.util;

import celephais.common.templates.EffectBase;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class EffectManager {
    public static ArrayList<EffectBase> effects = new ArrayList();
    public static ArrayList<EffectBase> toAdd = new ArrayList();

    public EffectManager() {
    }

    public static void addEffect(EffectBase e) {
        toAdd.add(e);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            for(int i = 0; i < effects.size(); ++i) {
                if (!((EffectBase)effects.get(i)).dead) {
                    ((EffectBase)effects.get(i)).update();
                }
            }
        }

        if (event.phase == TickEvent.Phase.END) {
            for(int i = 0; i < toAdd.size(); ++i) {
                effects.add(toAdd.get(i));
            }

            toAdd.clear();

            for(int i = 0; i < effects.size(); ++i) {
                if (((EffectBase)effects.get(i)).dead) {
                    effects.remove(i);
                    i = Math.max(0, i - 1);
                }
            }
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        for(int i = 0; i < effects.size(); ++i) {
            ((EffectBase)effects.get(i)).renderTotal(event.getPartialTicks());
        }

    }
}
