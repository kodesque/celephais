package celephais.client.fx;

import celephais.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EffectBeam extends Effect {
    public double x2 = (double)0.0F;
    public double y2 = (double)0.0F;
    public double z2 = (double)0.0F;
    public float thickness = 0.0F;

    public EffectBeam() {
    }

    public EffectBeam(int id) {
        super(id);
    }

    public EffectBeam setTarget(double x2, double y2, double z2) {
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        return this;
    }

    public EffectBeam setThickness(float thick) {
        this.thickness = thick;
        return this;
    }

    public void read(NBTTagCompound tag) {
        super.read(tag);
        this.x2 = tag.getDouble("x2");
        this.y2 = tag.getDouble("y2");
        this.z2 = tag.getDouble("z2");
        this.thickness = tag.getFloat("thick");
    }

    public NBTTagCompound write() {
        NBTTagCompound tag = super.write();
        tag.setDouble("x2", this.x2);
        tag.setDouble("y2", this.y2);
        tag.setDouble("z2", this.z2);
        tag.setFloat("thick", this.thickness);
        return tag;
    }

    @SideOnly(Side.CLIENT)
    public void render(float pticks) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderUtil.beam_texture);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
        RenderUtil.renderBeam(buffer, this.x, this.y, this.z, this.x2 * 0.1 + this.x * 0.9, this.y2 * 0.1 + this.y * 0.9, this.z2 * 0.1 + this.z * 0.9, this.r, this.g, this.b, 0.0F, this.r, this.g, this.b, this.a * this.getLifeCoeff(pticks), (double)this.thickness, (double)30.0F);
        RenderUtil.renderBeam(buffer, this.x2 * 0.1 + this.x * 0.9, this.y2 * 0.1 + this.y * 0.9, this.z2 * 0.1 + this.z * 0.9, this.x2 * 0.9 + this.x * 0.1, this.y2 * 0.9 + this.y * 0.1, this.z2 * 0.9 + this.z * 0.1, this.r, this.g, this.b, this.a * this.getLifeCoeff(pticks), this.r, this.g, this.b, this.a * this.getLifeCoeff(pticks), (double)this.thickness, (double)30.0F);
        RenderUtil.renderBeam(buffer, this.x2 * 0.9 + this.x * 0.1, this.y2 * 0.9 + this.y * 0.1, this.z2 * 0.9 + this.z * 0.1, this.x2, this.y2, this.z2, this.r, this.g, this.b, this.a * this.getLifeCoeff(pticks), this.r, this.g, this.b, 0.0F, (double)this.thickness, (double)30.0F);
        tess.draw();
    }
}
