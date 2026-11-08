package celephais.util;

import celephais.util.des.BFUVDeserializer;
import celephais.util.des.BPDeserializer;
import celephais.util.des.BPFDeserializer;
import celephais.util.des.ICTDeserializer;
import celephais.util.des.IODeserializer;
import celephais.util.des.ITV3FDeserializer;
import com.google.gson.GsonBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class Hax {
    public static Field bakedQuadFace;
    public static Field bakedQuadDiffuse;
    public static Field bakedQuadTint;
    public static Field field_ModelBakery_blockModelShapes;

    public Hax() {
    }

    public static void init() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Field f = ReflectionHelper.findField(ModelBlock.class, new String[]{"SERIALIZER", "field_178319_a"});
        f.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(f, f.getModifiers() & -17);
        f.set((Object)null, (new GsonBuilder()).registerTypeAdapter(ModelBlock.class,
                new ModelBlock.Deserializer()).registerTypeAdapter(BlockPart.class,
                new BPDeserializer()).registerTypeAdapter(BlockPartFace.class,
                new BPFDeserializer()).registerTypeAdapter(BlockFaceUV.class,
                new BFUVDeserializer()).registerTypeAdapter(ItemTransformVec3f.class,
                new ITV3FDeserializer()).registerTypeAdapter(ItemCameraTransforms.class,
                new ICTDeserializer()).registerTypeAdapter(ItemOverride.class,
                new IODeserializer()).create());
        field_ModelBakery_blockModelShapes = ReflectionHelper.findField(ModelBakery.class, new String[]{"blockModelShapes", "field_177610_k"});
        field_ModelBakery_blockModelShapes.setAccessible(true);
        bakedQuadFace = ReflectionHelper.findField(BakedQuad.class, new String[]{"face", "field_178214_c"});
        bakedQuadFace.setAccessible(true);
        bakedQuadTint = ReflectionHelper.findField(BakedQuad.class, new String[]{"tintIndex", "field_178213_b"});
        bakedQuadTint.setAccessible(true);
        bakedQuadDiffuse = ReflectionHelper.findField(BakedQuad.class, new String[]{"applyDiffuseLighting", "field_178214_f"});
        bakedQuadDiffuse.setAccessible(true);
    }

    public static Class findClass(Class[] classes, String name) {
        for(Class c : classes) {
            if (c.getTypeName().contains(name)) {
                return c;
            }
        }

        return null;
    }
}
