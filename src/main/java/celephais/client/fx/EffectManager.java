package celephais.client.fx;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class EffectManager {
    public static ArrayList<Effect> effects = new ArrayList();
    public static ArrayList<Effect> toAdd = new ArrayList();

    public EffectManager() {
    }

    public static void addEffect(Effect e) {
        toAdd.add(e);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            for(int i = 0; i < effects.size(); ++i) {
                if (!((Effect)effects.get(i)).dead) {
                    ((Effect)effects.get(i)).update();
                }
            }
        }

        if (event.phase == TickEvent.Phase.END) {
            for(int i = 0; i < toAdd.size(); ++i) {
                effects.add(toAdd.get(i));
            }

            toAdd.clear();

            for(int i = 0; i < effects.size(); ++i) {
                if (((Effect)effects.get(i)).dead) {
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
            ((Effect)effects.get(i)).renderTotal(event.getPartialTicks());
        }

    }
}
