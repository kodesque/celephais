package celephais.util;

import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CustomModelLoader implements ICustomModelLoader {
    public static Map<ResourceLocation, IModel> blockmodels = new HashMap();
    public static Map<ResourceLocation, IModel> itemmodels = new HashMap();
    public static FaceBakery faceBakery = new FaceBakery();

    public CustomModelLoader() {
    }

    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {
        blockmodels.clear();
        itemmodels.clear();
        MinecraftForge.EVENT_BUS.post(new RegisterCustomModelsEvent());
    }

    public boolean accepts(@Nonnull ResourceLocation modelLocation) {
        return blockmodels.containsKey(modelLocation) || itemmodels.containsKey(modelLocation);
    }

    @Nullable
    public IModel loadModel(@Nonnull ResourceLocation modelLocation) {
        if (blockmodels.containsKey(modelLocation)) {
            return (IModel)blockmodels.get(modelLocation);
        } else {
            return itemmodels.containsKey(modelLocation) ? (IModel)itemmodels.get(modelLocation) : null;
        }
    }
}