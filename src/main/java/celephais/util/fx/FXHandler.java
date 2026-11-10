package celephais.util.fx;

import celephais.client.fx.EffectBeam;
import celephais.util.EffectManager;
import celephais.client.fx.EffectSlash;
import celephais.network.proxy.ClientProxy;
import celephais.util.LibRegistry;
import epicsquid.mysticallib.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FXHandler {
    public static int FX_BEAM;
    public static int FX_SLASH;
    public static int FX_BURST;

    public FXHandler() {
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRegisterFX(RegisterFXEvent event) {
        FX_BEAM = FXRegistry.registerEffect((nbt) -> {
            EffectBeam beam = new EffectBeam();
            beam.read(nbt);
            EffectManager.addEffect(beam);
            return null;
        });
        FX_SLASH = FXRegistry.registerEffect((nbt) -> {
            EffectSlash slash = new EffectSlash();
            slash.read(nbt);
            EffectManager.addEffect(slash);
            return null;
        });
        FX_BURST = FXRegistry.registerEffect((nbt) -> {
            for(int i = 0; i < nbt.getInteger("count"); ++i) {
                float yaw = Util.rand.nextFloat() * 2.0F * (float)Math.PI;
                float pitch = Util.rand.nextFloat() * 2.0F * (float)Math.PI;
                float speed = Util.rand.nextFloat() * (float)nbt.getDouble("speed");
                float vx = MathHelper.cos(pitch) * MathHelper.sin(yaw) * speed;
                float vy = MathHelper.sin(pitch) * speed;
                float vz = MathHelper.cos(pitch) * MathHelper.cos(yaw) * speed;
                float solid = Util.rand.nextFloat();
                ClientProxy.particleRenderer.spawnParticle(Minecraft.getMinecraft().world, LibRegistry.PARTICLE_SMOKE, nbt.getDouble("x"), nbt.getDouble("y"), nbt.getDouble("z"), (double)(vx * 0.25F), (double)Math.abs(vy * 0.5F), (double)(vz * 0.25F), new double[]{(double)nbt.getInteger("lifetime"), (double)(0.25F * solid), (double)(0.25F * solid), (double)(0.25F * solid), (double)(0.5F * solid), nbt.getDouble("scale") * (double)(0.5F + Util.rand.nextFloat() * 0.5F), (double)0.75F});
                if (Util.rand.nextInt(4) == 0) {
                    ClientProxy.particleRenderer.spawnParticle(Minecraft.getMinecraft().world, LibRegistry.PARTICLE_SPARK, nbt.getDouble("x"), nbt.getDouble("y"), nbt.getDouble("z"), (double)vx, (double)Math.abs(vy * 0.5F), (double)vz, new double[]{(double)((float)nbt.getInteger("lifetime") * 0.5F), (double)0.25F, (double)0.25F, (double)1.0F, (double)1.0F, (double)(0.5F * Util.rand.nextFloat()) * nbt.getDouble("scale") * (double)(0.5F + Util.rand.nextFloat() * 0.5F), (double)1.5F});
                }
            }

            return null;
        });
    }
}
