package celephais.client.model;

import celephais.util.CustomModelLoader;
import celephais.util.ICustomModeledObject;
import celephais.util.LibRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;

public class ItemBase extends Item implements IModeledObject, ICustomModeledObject {
    private boolean hasCustomModel = false;

    public ItemBase(@Nonnull String name) {
        this.setTranslationKey(name);
        this.setRegistryName(LibRegistry.getActiveModid(), name);
    }

    public ItemBase setModelCustom(boolean custom) {
        this.hasCustomModel = custom;
        return this;
    }

    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "handlers"));
    }

    public void initCustomModel() {
        if (this.hasCustomModel) {
            CustomModelLoader.itemmodels.put(this.getRegistryName(), new CustomModelItem(false, new ResourceLocation[]{new ResourceLocation(this.getRegistryName().getNamespace() + ":items/" + this.getRegistryName().getPath())}));
        }

    }
}