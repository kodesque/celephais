package celephais.common.entity;

import celephais.client.fx.Effect;
import celephais.client.fx.EffectCut;
import celephais.client.fx.EffectSlash;
import celephais.client.fx.FXHandler;
import celephais.events.ModRegistryHandler;
import celephais.network.PacketHandler;
import celephais.util.MessageEffect;
import celephais.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntitySlash extends Entity {
    public static final DataParameter<Integer> lifetime;
    public EntityPlayer player = null;

    public EntitySlash(World worldIn) {
        super(worldIn);
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

    public void onUpdate() {
        if ((Integer)this.getDataManager().get(lifetime) % 2 == 0 && !this.world.isRemote) {
            float offX = 0.5F * (float)Math.sin(Math.toRadians((double)(-90.0F - this.player.rotationYaw)));
            float offZ = 0.5F * (float)Math.cos(Math.toRadians((double)(-90.0F - this.player.rotationYaw)));
            double x1 = this.player.posX + this.player.getLookVec().x * (double)0.5F + (double)offX;
            double y1 = this.player.posY + this.player.getLookVec().y * (double)0.5F + (double)this.player.getEyeHeight();
            double z1 = this.player.posZ + this.player.getLookVec().z * (double)0.5F + (double)offZ;
            double x2 = this.player.posX + this.player.getLookVec().x * (double)4.0F;
            double y2 = this.player.posY + (double)this.player.getEyeHeight() + this.player.getLookVec().y * (double)4.0F;
            double z2 = this.player.posZ + this.player.getLookVec().z * (double)4.0F;
            this.posX = this.player.posX;
            this.posY = this.player.posY;
            this.posZ = this.player.posZ;
            Effect slash = (new EffectSlash(this.player.world.provider.getDimension())).setSlashProperties(this.player.rotationYaw, this.player.rotationPitch, 30.0F + Util.rand.nextFloat() * 120.0F, 2.0F, 1.5F, 120.0F).setPosition(x1, y1, z1).setMotion((x2 - x1) / (double)5.0F, (y2 - y1) / (double)5.0F, (z2 - z1) / (double)5.0F).setLife(5).setAdditive(true).setColor(0.35F, 0.35F, 1.0F, 1.0F);
            PacketHandler.INSTANCE.sendToAll(new MessageEffect(FXHandler.FX_SLASH, slash.write()));
            double lx = this.player.posX + this.player.getLookVec().x * (double)2.0F;
            double ly = this.player.posY + (double)this.player.getEyeHeight() + this.player.getLookVec().y * (double)2.0F;
            double lz = this.player.posZ + this.player.getLookVec().z * (double)2.0F;

            for(EntityLivingBase e : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(lx - (double)2.0F, ly - (double)2.0F, lz - (double)2.0F, lx + (double)2.0F, ly + (double)2.0F, lz + (double)2.0F))) {
                if (e.getUniqueID().compareTo(this.player.getUniqueID()) != 0) {
                    e.hurtResistantTime = 0;
                    e.setRevengeTarget(this.player);
                    if (e.getHealth() > 0.0F) {
                        Effect cut = (new EffectCut(this.world.provider.getDimension())).setSlashProperties(this.player.rotationYaw, this.player.rotationPitch, Util.rand.nextFloat() * 360.0F).setColor(0.35F, 0.35F, 1.0F, 1.0F).setPosition(e.posX, e.posY + (double)(e.height / 2.0F), e.posZ).setAdditive(true).setLife(10);
                        PacketHandler.INSTANCE.sendToAll(new MessageEffect(ModRegistryHandler.FX_CUT, cut.write()));
                    }

                    e.attackEntityFrom(DamageSource.causePlayerDamage(this.player), 6.0F);
                }
            }
        }

        this.getDataManager().set(lifetime, (Integer)this.getDataManager().get(lifetime) - 1);
        if ((Integer)this.getDataManager().get(lifetime) <= 0) {
            this.setDead();
        }

    }

    protected void entityInit() {
        this.getDataManager().register(lifetime, 12);
        this.getDataManager().setDirty(lifetime);
    }

    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.setDead();
    }

    protected void writeEntityToNBT(NBTTagCompound compound) {
    }

    static {
        lifetime = EntityDataManager.createKey(epicsquid.crissaegrim.entity.EntitySlash.class, DataSerializers.VARINT);
    }
}