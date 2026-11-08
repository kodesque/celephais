package celephais.util;

import celephais.network.PacketHandler;
import celephais.network.proxy.ClientProxy;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class LibEvents {
    public static boolean acceptUpdates = true;
    public static Map<BlockPos, TileEntity> toUpdate = new HashMap();
    public static Map<BlockPos, TileEntity> overflow = new HashMap();
    public static int ticks = 0;
    Map<Integer, Integer> dimCounts = new HashMap();

    public LibEvents() {
    }

    public static void markForUpdate(@Nonnull BlockPos pos, @Nonnull TileEntity tile) {
        if (!tile.getWorld().isRemote && acceptUpdates) {
            if (!toUpdate.containsKey(pos)) {
                toUpdate.put(pos, tile);
            } else {
                toUpdate.replace(pos, tile);
            }
        } else if (!tile.getWorld().isRemote) {
            if (!overflow.containsKey(pos)) {
                overflow.put(pos, tile);
            } else {
                overflow.replace(pos, tile);
            }
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent event) {
        for(Map.Entry<String, ResourceLocation> e : ParticleRegistry.particleTextures.entrySet()) {
            event.getMap().registerSprite((ResourceLocation)e.getValue());
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ++ticks;
            ClientProxy.particleRenderer.updateParticles();
        }

    }

    @SubscribeEvent
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        PacketHandler.INSTANCE.sendToServer(new MessageLeftClickEmpty(event.getEntityPlayer(), event.getHand(), event.getItemStack()));
    }
}