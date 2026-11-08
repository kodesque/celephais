package celephais.client.fx;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class Effect {
    public int lifetime = 0;
    public int maxLife = 0;
    public float r = 0.0F;
    public float g = 0.0F;
    public float b = 0.0F;
    public float a = 0.0F;
    public boolean inited = false;
    public double x = (double)0.0F;
    public double y = (double)0.0F;
    public double z = (double)0.0F;
    public double px = (double)0.0F;
    public double py = (double)0.0F;
    public double pz = (double)0.0F;
    public double vx = (double)0.0F;
    public double vy = (double)0.0F;
    public double vz = (double)0.0F;
    public boolean additive = false;
    public boolean dead = false;
    public int dimId = 0;

    public Effect() {
    }

    public Effect(int id) {
        this.dimId = id;
    }

    public Effect setLife(int l) {
        this.maxLife = l;
        this.lifetime = l;
        return this;
    }

    public Effect setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this;
    }

    public Effect setPosition(double x, double y, double z) {
        this.px = x;
        this.py = y;
        this.pz = z;
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Effect setMotion(double vx, double vy, double vz) {
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        return this;
    }

    public Effect setAdditive(boolean additive) {
        this.additive = additive;
        return this;
    }

    public void update() {
        if (!this.inited) {
            this.inited = true;
        }

        this.px = this.x;
        this.py = this.y;
        this.pz = this.z;
        this.x += this.vx;
        this.y += this.vy;
        this.z += this.vz;
        --this.lifetime;
        if (this.lifetime < 0) {
            this.kill();
        }

    }

    public void kill() {
        this.dead = true;
    }

    public float getLifeCoeff(float pTicks) {
        return Math.max(0.0F, ((float)this.lifetime - pTicks) / (float)this.maxLife);
    }

    public float getInterpX(float pticks) {
        return (float)this.x * pticks + (float)this.px * (1.0F - pticks);
    }

    public float getInterpY(float pticks) {
        return (float)this.y * pticks + (float)this.py * (1.0F - pticks);
    }

    public float getInterpZ(float pticks) {
        return (float)this.z * pticks + (float)this.pz * (1.0F - pticks);
    }

    @SideOnly(Side.CLIENT)
    public void renderTotal(float pticks) {
        if (this.inited) {
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, this.additive ? GlStateManager.DestFactor.ONE : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.depthMask(false);
            int dfunc = GL11.glGetInteger(2932);
            GlStateManager.depthFunc(515);
            int func = GL11.glGetInteger(3009);
            float ref = GL11.glGetFloat(3010);
            GlStateManager.alphaFunc(519, 0.0F);
            GlStateManager.disableCull();
            GlStateManager.shadeModel(7425);
            GlStateManager.pushMatrix();
            GlStateManager.translate(-TileEntityRendererDispatcher.staticPlayerX, -TileEntityRendererDispatcher.staticPlayerY, -TileEntityRendererDispatcher.staticPlayerZ);
            this.render(pticks);
            GlStateManager.popMatrix();
            GlStateManager.alphaFunc(func, ref);
            GlStateManager.depthFunc(dfunc);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
        }

    }

    public void read(NBTTagCompound tag) {
        this.px = this.x;
        this.x = tag.getDouble("x");
        this.py = this.y;
        this.y = tag.getDouble("y");
        this.pz = this.z;
        this.z = tag.getDouble("z");
        this.vx = tag.getDouble("vx");
        this.vy = tag.getDouble("vy");
        this.vz = tag.getDouble("vz");
        this.r = tag.getFloat("r");
        this.g = tag.getFloat("g");
        this.b = tag.getFloat("b");
        this.a = tag.getFloat("a");
        this.maxLife = tag.getInteger("maxlife");
        this.lifetime = tag.getInteger("life");
        this.dimId = tag.getInteger("dim");
        this.additive = tag.getBoolean("additive");
    }

    public NBTTagCompound write() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setDouble("x", this.x);
        tag.setDouble("y", this.y);
        tag.setDouble("z", this.z);
        tag.setDouble("vx", this.vx);
        tag.setDouble("vy", this.vy);
        tag.setDouble("vz", this.vz);
        tag.setFloat("r", this.r);
        tag.setFloat("g", this.g);
        tag.setFloat("b", this.b);
        tag.setFloat("a", this.a);
        tag.setInteger("maxlife", this.maxLife);
        tag.setInteger("life", this.lifetime);
        tag.setInteger("dim", this.dimId);
        tag.setBoolean("additive", this.additive);
        return tag;
    }

    @SideOnly(Side.CLIENT)
    public void render(float pticks) {
    }
}
