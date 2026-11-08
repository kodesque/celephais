package celephais.client.fx;

import celephais.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EffectSlash extends Effect {
    public float yaw = 0.0F;
    public float pitch = 0.0F;
    public float slashAngle = 0.0F;
    public float slashWidth = 0.0F;
    public float slashRange = 0.0F;
    public float slashRadius = 0.0F;

    public EffectSlash() {
    }

    public EffectSlash(int id) {
        super(id);
    }

    public EffectSlash setSlashProperties(float yaw, float pitch, float angle, float radius, float thickness, float arc) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.slashAngle = angle;
        this.slashRadius = radius;
        this.slashWidth = thickness;
        this.slashRange = arc;
        return this;
    }

    public void read(NBTTagCompound tag) {
        super.read(tag);
        this.yaw = tag.getFloat("yaw");
        this.pitch = tag.getFloat("pitch");
        this.slashAngle = tag.getFloat("slashAngle");
        this.slashRadius = tag.getFloat("slashRadius");
        this.slashWidth = tag.getFloat("slashWidth");
        this.slashRange = tag.getFloat("slashRange");
    }

    public NBTTagCompound write() {
        NBTTagCompound tag = super.write();
        tag.setFloat("yaw", this.yaw);
        tag.setFloat("pitch", this.pitch);
        tag.setFloat("slashAngle", this.slashAngle);
        tag.setFloat("slashRadius", this.slashRadius);
        tag.setFloat("slashWidth", this.slashWidth);
        tag.setFloat("slashRange", this.slashRange);
        return tag;
    }

    @SideOnly(Side.CLIENT)
    public void render(float pticks) {
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderUtil.glow_texture);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();
        GlStateManager.translate(this.getInterpX(pticks), this.getInterpY(pticks), this.getInterpZ(pticks));
        GlStateManager.rotate(-this.yaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(this.pitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-this.slashAngle, 0.0F, 0.0F, 1.0F);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
        RenderUtil.renderSlash(buffer, (double)0.0F, (double)0.0F, (double)0.0F, this.r, this.g, this.b, this.a * this.getLifeCoeff(pticks), this.slashRadius, this.slashWidth, this.slashRange);
        tess.draw();
    }
}