package celephais.client.model;

import com.google.common.collect.ImmutableList;
import celephais.util.DefaultTransformations;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class BakedModelItem implements IBakedModel {
    protected Function<ResourceLocation, TextureAtlasSprite> getter;
    protected VertexFormat format;
    protected ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();
    protected List<TextureAtlasSprite> layers = new ArrayList();
    protected CustomModelItem model;
    @Nonnull
    protected List<BakedQuad> layerQuads = new ArrayList();

    public BakedModelItem(@Nonnull VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, @Nonnull CustomModelItem model) {
        this.getter = bakedTextureGetter;
        this.format = format;
        int i = 0;

        for(this.model = model; model.textures.containsKey("layer" + i); ++i) {
            this.layers.add(this.getter.apply(model.textures.get("layer" + i)));
            this.layerQuads.addAll(ItemLayerModel.getQuadsForSprite(i, (TextureAtlasSprite)this.layers.get(i), format, Optional.empty()));
        }

    }

    @Nonnull
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return this.layerQuads;
    }

    public boolean isAmbientOcclusion() {
        return true;
    }

    public boolean isGui3d() {
        return true;
    }

    public boolean isBuiltInRenderer() {
        return false;
    }

    @Nonnull
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    @Nonnull
    public ItemOverrideList getOverrides() {
        return new ItemOverrideList(Arrays.asList());
    }

    @Nonnull
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(@Nonnull ItemCameraTransforms.TransformType type) {
        if (this.model.handheld) {
            if (DefaultTransformations.handheldTransforms.containsKey(type)) {
                Matrix4f matrix = ((TRSRTransformation) DefaultTransformations.handheldTransforms.get(type)).getMatrix();
                return Pair.of(this, matrix);
            }
        } else if (DefaultTransformations.itemTransforms.containsKey(type)) {
            Matrix4f matrix = ((TRSRTransformation)DefaultTransformations.itemTransforms.get(type)).getMatrix();
            return Pair.of(this, matrix);
        }

        return ForgeHooksClient.handlePerspective(this, type);
    }
}
