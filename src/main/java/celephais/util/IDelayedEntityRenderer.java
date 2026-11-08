package celephais.util;

import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public interface IDelayedEntityRenderer {
    void renderLater(@Nonnull Entity var1, double var2, double var4, double var6, float var8, float var9);
}
