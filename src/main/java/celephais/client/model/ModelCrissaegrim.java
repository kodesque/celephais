package celephais.client.model;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.function.Function;

public class ModelCrissaegrim extends CustomModelBase {
    public boolean handheld = true;

    public ModelCrissaegrim() {
        this.addTexture("layer0", new ResourceLocation("crissaegrim:items/crissaegrim"));
    }

    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BakedModelCrissaegrim(state, format, bakedTextureGetter, this);
    }

    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }
}
