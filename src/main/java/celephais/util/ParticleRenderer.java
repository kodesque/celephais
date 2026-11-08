package celephais.util;

import celephais.network.proxy.ClientProxy;
import celephais.root.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

public class ParticleRenderer {
    private ArrayList<Particle> particles = new ArrayList();

    public ParticleRenderer() {
    }

    public synchronized void updateParticles() {
        Iterator<Particle> iterator = this.particles.iterator();

        while(iterator.hasNext()) {
            boolean doRemove = true;
            Particle particle = (Particle)iterator.next();
            if (particle != null && particle instanceof IParticle && ((IParticle)particle).alive()) {
                particle.onUpdate();
                doRemove = false;
            }

            if (doRemove) {
                iterator.remove();
            }
        }

    }

    public void renderParticles(@Nonnull EntityPlayer dumbplayer, float partialTicks) {
        float f = ActiveRenderInfo.getRotationX();
        float f1 = ActiveRenderInfo.getRotationZ();
        float f2 = ActiveRenderInfo.getRotationYZ();
        float f3 = ActiveRenderInfo.getRotationXY();
        float f4 = ActiveRenderInfo.getRotationXZ();
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null) {
            Particle.interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
            Particle.interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
            Particle.interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
            Particle.cameraViewDir = player.getLook(partialTicks);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.alphaFunc(516, 0.003921569F);
            GlStateManager.disableCull();
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Tessellator tess = Tessellator.getInstance();
            BufferBuilder buffer = tess.getBuffer();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

            for(Particle particle : this.particles) {
                if (particle instanceof IParticle && !((IParticle)particle).isAdditive()) {
                    particle.renderParticle(buffer, player, partialTicks, f, f4, f1, f2, f3);
                }
            }

            tess.draw();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

            for(Particle particle : this.particles) {
                if (particle != null && ((IParticle)particle).isAdditive()) {
                    particle.renderParticle(buffer, player, partialTicks, f, f4, f1, f2, f3);
                }
            }

            tess.draw();
            GlStateManager.disableDepth();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

            for(Particle particle : this.particles) {
                if (particle instanceof IParticle && !((IParticle)particle).isAdditive() && ((IParticle)particle).renderThroughBlocks()) {
                    particle.renderParticle(buffer, player, partialTicks, f, f4, f1, f2, f3);
                }
            }

            tess.draw();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

            for(Particle particle : this.particles) {
                if (particle != null && ((IParticle)particle).isAdditive() && ((IParticle)particle).renderThroughBlocks()) {
                    particle.renderParticle(buffer, player, partialTicks, f, f4, f1, f2, f3);
                }
            }

            tess.draw();
            GlStateManager.enableDepth();
            GlStateManager.enableCull();
            GlStateManager.depthMask(true);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.enableAlpha();
        }

    }

    public void spawnParticle(World world, String particle, double x, double y, double z, double vx, double vy, double vz, double... data) {
        if (Main.proxy instanceof ClientProxy) {
            try {
                this.particles.add((Particle) ((Constructor) ParticleRegistry.getParticles().get(particle)).newInstance(world, x, y, z, vx, vy, vz, data));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
                ((Exception)e).printStackTrace();
            }
        }

    }
}
