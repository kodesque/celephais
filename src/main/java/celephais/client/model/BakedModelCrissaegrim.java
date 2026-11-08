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
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class BakedModelCrissaegrim implements IBakedModel {
    Function<ResourceLocation, TextureAtlasSprite> getter;
    VertexFormat format;
    ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();
    List<TextureAtlasSprite> layers = new ArrayList();
    ModelCrissaegrim model = null;
    BakedModelEmpty empty = new BakedModelEmpty();

    public BakedModelCrissaegrim(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, ModelCrissaegrim model) {
        this.getter = bakedTextureGetter;
        this.format = format;
        int i = 0;

        for(this.model = model; model.textures.containsKey("layer" + i); ++i) {
            this.layers.add(this.getter.apply(model.textures.get("layer" + i)));
        }

    }

    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        List<BakedQuad> quads = new ArrayList();

        for(int i = 0; i < this.layers.size(); ++i) {
            quads.addAll(ItemLayerModel.getQuadsForSprite(16777215, (TextureAtlasSprite)this.layers.get(i), this.format, Optional.empty()));
        }

        return quads;
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

    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    public ItemOverrideList getOverrides() {
        return new ItemOverrideList(Arrays.asList());
    }

    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType type) {
        Matrix4f matrix = null;
        if (DefaultTransformations.handheldTransforms.containsKey(type)) {
            matrix = ((TRSRTransformation)DefaultTransformations.handheldTransforms.get(type)).getMatrix();
            if (type != ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND && type != ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND && type != ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND && type == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND) {
            }

            return Pair.of(this, matrix);
        } else {
            return ForgeHooksClient.handlePerspective(this, type);
        }
    }
}
