package celephais.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;

public class BakedModelColorWrapper implements IBakedModel {
    private IBakedModel internal;
    private List<BakedQuad> quads = new ArrayList();

    public BakedModelColorWrapper(@Nonnull IBakedModel model) {
        this.internal = model;
    }

    @Nonnull
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if (this.quads.size() == 0) {
            List<BakedQuad> list = new ArrayList();

            for(EnumFacing f : EnumFacing.values()) {
                list.addAll(this.internal.getQuads(state, f, rand));
            }

            list.addAll(this.internal.getQuads(state, (EnumFacing)null, rand));

            for(int i = 0; i < list.size(); ++i) {
                try {
                    Hax.bakedQuadTint.set(list.get(i), 0);
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    ((Exception)e).printStackTrace();
                }
            }

            this.quads.addAll(list);
        }

        return this.quads;
    }

    public boolean isAmbientOcclusion() {
        return this.internal.isAmbientOcclusion();
    }

    public boolean isGui3d() {
        return this.internal.isGui3d();
    }

    public boolean isBuiltInRenderer() {
        return this.internal.isBuiltInRenderer();
    }

    @Nonnull
    public TextureAtlasSprite getParticleTexture() {
        return this.internal.getParticleTexture();
    }

    @Nonnull
    public ItemOverrideList getOverrides() {
        return this.internal.getOverrides();
    }

    @Nonnull
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(@Nonnull ItemCameraTransforms.TransformType type) {
        return this.internal.handlePerspective(type);
    }
}