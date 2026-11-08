package celephais.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ParticleBase extends Particle implements IParticle {
    private int lifetime = 0;

    public ParticleBase(@Nonnull World world, double x, double y, double z, double vx, double vy, double vz, double[] data) {
        super(world, x, y, z, (double)0.0F, (double)0.0F, (double)0.0F);
        this.motionX = vx;
        this.motionY = vy;
        this.motionZ = vz;
        if (data.length >= 1) {
            this.lifetime = (int)data[0];
        }

        this.particleMaxAge = this.lifetime;
        ResourceLocation texture = (ResourceLocation) ParticleRegistry.particleTextures.get(Util.getLowercaseClassName(this.getClass()));
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
        this.setParticleTexture(sprite);
        this.particleScale = 1.0F;
        this.canCollide = false;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionX *= (double)0.95F;
        this.motionY *= (double)0.95F;
        this.motionZ *= (double)0.95F;
        --this.lifetime;
    }

    public int getFXLayer() {
        return 1;
    }

    public boolean alive() {
        return this.lifetime > 0;
    }

    public boolean isAdditive() {
        return false;
    }

    public boolean renderThroughBlocks() {
        return false;
    }
}
