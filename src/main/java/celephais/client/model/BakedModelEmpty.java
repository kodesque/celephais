package celephais.client.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

public class BakedModelEmpty implements IBakedModel {
    public BakedModelEmpty() {
    }

    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        return new ArrayList();
    }

    public boolean isAmbientOcclusion() {
        return false;
    }

    public boolean isGui3d() {
        return false;
    }

    public boolean isBuiltInRenderer() {
        return false;
    }

    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    public ItemOverrideList getOverrides() {
        return null;
    }
}