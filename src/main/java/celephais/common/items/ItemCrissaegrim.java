package celephais.common.items;

import celephais.common.templates.EffectBase;
import celephais.client.fx.EffectCut;
import celephais.common.entity.EntitySlash;
import celephais.common.init.ItemInit;
import celephais.events.ModRegistryHandler;
import celephais.network.PacketHandler;
import celephais.root.Main;
import celephais.network.packets.PacketEffect;
import epicsquid.mysticallib.util.Util;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class ItemCrissaegrim extends Item {
    
    public static final String durability = "durability";

    public ItemCrissaegrim(String name) {
        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);

        this.setCreativeTab(CreativeTabs.COMBAT);

        this.setMaxStackSize(1);

        ItemInit.ITEMS.add(this);
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (selected && world.getCurrentMoonPhaseFactor() > 0.875F && !world.isDaytime()) {
            NBTTagCompound tag = stack.getSubCompound("celephais");
            if (tag != null && tag.hasKey(durability) && !world.isRemote && tag.getInteger(durability) > 0) {
                tag.setInteger(durability, Math.max(0, tag.getInteger(durability) - 5));
                if (entity.ticksExisted % 1 == 0) {
                    float offX = 0.75F * (float)Math.sin(Math.toRadians((double)(-30.0F - entity.rotationYaw)));
                    float offZ = 0.75F * (float)Math.cos(Math.toRadians((double)(-30.0F - entity.rotationYaw)));
                    EffectBase cut = (new EffectCut(world.provider.getDimension())).setSlashProperties(0.0F, 0.0F, 90.0F).setLife(20).setColor(0.35F, 0.35F, 1.0F, 1.0F).setAdditive(true).setPosition(entity.posX + (double)(0.5F * (Util.rand.nextFloat() - 0.5F)) + (double)offX, entity.posY + (double)(0.5F * (Util.rand.nextFloat() - 0.5F)) + (double)(entity.height / 2.0F), entity.posZ + (double)(0.5F * (Util.rand.nextFloat() - 0.5F)) + (double)offZ);
                    PacketHandler.INSTANCE.sendToAll(new PacketEffect(ModRegistryHandler.FX_CUT, cut.write()));
                }
            }
        }

    }

    public boolean showDurabilityBar(ItemStack stack) {
        return stack.hasTagCompound() && stack.getSubCompound(Main.MODID) != null && stack.getSubCompound(Main.MODID).hasKey(durability) && stack.getSubCompound(Main.MODID).getInteger(durability) > 0;
    }

    public double getDurabilityForDisplay(ItemStack stack) {
        return (double)((float)stack.getSubCompound(Main.MODID).getInteger(durability) / 10917.0F);
    }

    public static void doSlash(EntityPlayer p) {
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

        NBTTagCompound tag = p.getHeldItemMainhand().getOrCreateSubCompound(Main.MODID);
        if (tag.hasKey(durability)) {
            tag.setInteger(durability, tag.getInteger(durability) + 1);
        } else {
            tag.setInteger(durability, 1);
        }

        ItemStack s = p.getHeldItemMainhand().copy();
        if (tag.getInteger(durability) > 10917) {
            p.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
        } else {
            p.setHeldItem(EnumHand.MAIN_HAND, s);
        }

    }
}
