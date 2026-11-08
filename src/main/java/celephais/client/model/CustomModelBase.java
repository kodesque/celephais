package celephais.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CustomModelBase implements IModel {
    public Map<String, ResourceLocation> textures = new HashMap();

    public CustomModelBase() {
    }

    @Nonnull
    public CustomModelBase addTexture(@Nonnull String name, @Nonnull ResourceLocation texture) {
        this.textures.put(name, texture);
        return this;
    }

    @Nonnull
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableList.of();
    }

    @Nonnull
    public Collection<ResourceLocation> getTextures() {
        return this.textures.values();
    }

    @Nonnull
    public IBakedModel bake(@Nullable IModelState state, @Nonnull VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BakedModelNull(DefaultVertexFormats.BLOCK, bakedTextureGetter);
    }

    @Nonnull
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }
}
