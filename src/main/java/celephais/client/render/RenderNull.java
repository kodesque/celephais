package celephais.client.render;

import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderNull extends RenderEntity {
    public RenderNull(@Nonnull RenderManager renderManager) {
        super(renderManager);
    }

    public void doRender(@Nonnull Entity entity, double x, double y, double z, float yaw, float pTicks) {
    }

    public void doRenderShadowAndFire(@Nonnull Entity entity, double x, double y, double z, float yaw, float pTicks) {
    }

    public static class Factory implements IRenderFactory {
        public Factory() {
        }

        public RenderEntity createRenderFor(@Nonnull RenderManager manager) {
            return new RenderNull(manager);
        }
    }
}