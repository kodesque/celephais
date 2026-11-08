package celephais.client.model;

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
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class BakedModelNull implements IBakedModel {
    protected Function<ResourceLocation, TextureAtlasSprite> getter;
    protected VertexFormat format;

    public BakedModelNull(@Nullable VertexFormat format, @Nullable Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        this.getter = bakedTextureGetter;
        this.format = format;
    }

    @Nonnull
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return new ArrayList();
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
        if (DefaultTransformations.blockTransforms.containsKey(type)) {
            Matrix4f matrix = ((TRSRTransformation) DefaultTransformations.blockTransforms.get(type)).getMatrix();
            return Pair.of(this, matrix);
        } else {
            return ForgeHooksClient.handlePerspective(this, type);
        }
    }
}
