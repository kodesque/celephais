package celephais.client.fx;

import celephais.util.ParticleBase;
import net.minecraft.world.World;

public class ParticleFlame extends ParticleBase {
    public float colorR = 0.0F;
    public float colorG = 0.0F;
    public float colorB = 0.0F;
    public float initScale = 0.0F;
    public float initAlpha = 0.0F;

    public ParticleFlame(World world, double x, double y, double z, double vx, double vy, double vz, double[] data) {
        super(world, x, y, z, vx, vy, vz, data);
        this.colorR = (float)data[1];
        this.colorG = (float)data[2];
        this.colorB = (float)data[3];
        if ((double)this.colorR > (double)1.0F) {
            this.colorR /= 255.0F;
        }

        if ((double)this.colorG > (double)1.0F) {
            this.colorG /= 255.0F;
        }

        if ((double)this.colorB > (double)1.0F) {
            this.colorB /= 255.0F;
        }

        this.setRBGColorF(this.colorR, this.colorG, this.colorB);
        this.setAlphaF((float)data[4]);
        this.initAlpha = (float)data[4];
        this.particleScale = (float)data[5];
        this.initScale = (float)data[5];
    }

    public void onUpdate() {
        super.onUpdate();
        float lifeCoeff = (float)this.particleAge / (float)this.particleMaxAge;
        this.particleScale = this.initScale - this.initScale * lifeCoeff;
        this.particleAlpha = this.initAlpha * (1.0F - lifeCoeff);
    }

    public boolean isAdditive() {
        return true;
    }
}
