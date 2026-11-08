package celephais.client.model;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

public class CustomModelItem extends CustomModelBase {
    public boolean handheld;

    public CustomModelItem(boolean handheld, @Nonnull ResourceLocation... textures) {
        this.handheld = handheld;

        for(int i = 0; i < textures.length; ++i) {
            this.addTexture("layer" + i, textures[i]);
        }

    }

    @Nonnull
    public IBakedModel bake(@Nullable IModelState state, @Nonnull VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BakedModelItem(format, bakedTextureGetter, this);
    }

    @Nonnull
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }
}
