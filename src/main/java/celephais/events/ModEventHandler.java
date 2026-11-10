package celephais.events;

import celephais.common.items.ItemCrissaegrim;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModEventHandler {
    public ModEventHandler() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onAttack(LivingAttackEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer && ((EntityPlayer) event.getSource().getTrueSource()).getHeldItemMainhand().getItem() instanceof ItemCrissaegrim) {
            EntityPlayer p = (EntityPlayer)event.getSource().getTrueSource();
            ItemCrissaegrim.doSlash(p);
        }

    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public void onHarvest(PlayerEvent.BreakSpeed event) {
        if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemCrissaegrim) {
            event.setNewSpeed(0.0F);
        }

    }

    @SubscribeEvent
    public void slashAir(PlayerServerInteractEvent.LeftClickAir event) {
        if (event.getPlayer() instanceof EntityPlayer && event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemCrissaegrim && !event.getPlayer().world.isRemote) {
            EntityPlayer p = event.getPlayer();
            ItemCrissaegrim.doSlash(p);
        }

    }

    @SubscribeEvent
    public void slashBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntityPlayer() instanceof EntityPlayer && !event.getEntityPlayer().getHeldItemMainhand().isEmpty() && event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemCrissaegrim && event.getEntityPlayer().world != null && !event.getEntityPlayer().world.isRemote) {
            EntityPlayer p = event.getEntityPlayer();
            ItemCrissaegrim.doSlash(p);
        }

    }
}