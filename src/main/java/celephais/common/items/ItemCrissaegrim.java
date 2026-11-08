package celephais.common.items;

import celephais.client.fx.Effect;
import celephais.client.fx.EffectCut;
import celephais.client.model.ItemBase;
import celephais.client.model.ModelCrissaegrim;
import celephais.events.ModRegistryHandler;
import celephais.network.PacketHandler;
import celephais.util.CustomModelLoader;
import celephais.util.ICustomModeledObject;
import celephais.util.MessageEffect;
import celephais.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCrissaegrim extends ItemBase implements ICustomModeledObject {
    public ItemCrissaegrim(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    public void initCustomModel() {
        CustomModelLoader.itemmodels.put(this.getRegistryName(), new ModelCrissaegrim());
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (selected && world.getCurrentMoonPhaseFactor() > 0.875F && !world.isDaytime()) {
            NBTTagCompound tag = stack.getSubCompound("celephais");
            if (tag != null && tag.hasKey("durability") && !world.isRemote && tag.getInteger("durability") > 0) {
                tag.setInteger("durability", Math.max(0, tag.getInteger("durability") - 5));
                if (entity.ticksExisted % 1 == 0) {
                    float offX = 0.75F * (float)Math.sin(Math.toRadians((double)(-30.0F - entity.rotationYaw)));
                    float offZ = 0.75F * (float)Math.cos(Math.toRadians((double)(-30.0F - entity.rotationYaw)));
                    Effect cut = (new EffectCut(world.provider.getDimension())).setSlashProperties(0.0F, 0.0F, 90.0F).setLife(20).setColor(0.35F, 0.35F, 1.0F, 1.0F).setAdditive(true).setPosition(entity.posX + (double)(0.5F * (Util.rand.nextFloat() - 0.5F)) + (double)offX, entity.posY + (double)(0.5F * (Util.rand.nextFloat() - 0.5F)) + (double)(entity.height / 2.0F), entity.posZ + (double)(0.5F * (Util.rand.nextFloat() - 0.5F)) + (double)offZ);
                    PacketHandler.INSTANCE.sendToAll(new MessageEffect(ModRegistryHandler.FX_CUT, cut.write()));
                }
            }
        }

    }

    public boolean showDurabilityBar(ItemStack stack) {
        return stack.hasTagCompound() && stack.getSubCompound("celephais") != null && stack.getSubCompound("celephais").hasKey("durability") && stack.getSubCompound("celephais").getInteger("durability") > 0;
    }

    public double getDurabilityForDisplay(ItemStack stack) {
        return (double)((float)stack.getSubCompound("celephais").getInteger("durability") / 10917.0F);
    }
}
