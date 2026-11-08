package celephais.events;

import celephais.common.entity.EntitySlash;
import celephais.common.items.ItemCrissaegrim;
import celephais.util.PlayerServerInteractEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class ModEventHandler {
    public ModEventHandler() {
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public void onAttack(LivingAttackEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer && ((EntityPlayer)((EntityPlayer)event.getSource().getTrueSource())).getHeldItemMainhand().getItem() instanceof ItemCrissaegrim) {
            EntityPlayer p = (EntityPlayer)event.getSource().getTrueSource();
            this.doSlash(p);
        }

    }

    public void doSlash(EntityPlayer p) {
        List<EntitySlash> slashes = p.world.getEntitiesWithinAABB(EntitySlash.class, p.getEntityBoundingBox().expand((double)-2.0F, (double)-2.0F, (double)-2.0F).expand((double)2.0F, (double)2.0F, (double)2.0F));
        boolean foundSlash = false;

        for(EntitySlash s : slashes) {
            if (s.player != null && s.player.getUniqueID().compareTo(p.getUniqueID()) == 0) {
                s.getDataManager().set(EntitySlash.lifetime, 12);
                s.getDataManager().setDirty(EntitySlash.lifetime);
                foundSlash = true;
                break;
            }
        }

        if (!foundSlash) {
            EntitySlash s = new EntitySlash(p.world);
            s.setPlayer(p);
            s.setPosition(p.posX, p.posY, p.posZ);
            p.world.spawnEntity(s);
        }

        NBTTagCompound tag = p.getHeldItemMainhand().getOrCreateSubCompound("celephais");
        if (tag.hasKey("durability")) {
            tag.setInteger("durability", tag.getInteger("durability") + 1);
        } else {
            tag.setInteger("durability", 1);
        }

        ItemStack s = p.getHeldItemMainhand().copy();
        if (tag.getInteger("durability") > 10917) {
            p.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
        } else {
            p.setHeldItem(EnumHand.MAIN_HAND, s);
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
            this.doSlash(p);
        }

    }

    @SubscribeEvent
    public void slashBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntityPlayer() instanceof EntityPlayer && !event.getEntityPlayer().getHeldItemMainhand().isEmpty() && event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemCrissaegrim && event.getEntityPlayer().world != null && !event.getEntityPlayer().world.isRemote) {
            EntityPlayer p = event.getEntityPlayer();
            this.doSlash(p);
        }

    }
}