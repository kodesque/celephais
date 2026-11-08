package celephais.util;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.HashMap;
import java.util.Map;

public class DefaultTransformations {
    public static Map<ItemCameraTransforms.TransformType, TRSRTransformation> itemTransforms = new HashMap();
    public static Map<ItemCameraTransforms.TransformType, TRSRTransformation> blockTransforms = new HashMap();
    public static Map<ItemCameraTransforms.TransformType, TRSRTransformation> handheldTransforms = new HashMap();
    public static TRSRTransformation ITEM_GROUND = create(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5F);
    public static TRSRTransformation ITEM_HEAD = create(0.0F, 13.0F, 7.0F, 0.0F, 180.0F, 0.0F, 1.0F);
    public static TRSRTransformation ITEM_FIXED = create(0.0F, 0.0F, 0.0F, 0.0F, 180.0F, 0.0F, 1.0F);
    public static TRSRTransformation ITEM_THIRD_PERSON_RIGHT = create(0.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.55F);
    public static TRSRTransformation ITEM_THIRD_PERSON_LEFT = create(0.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.55F);
    public static TRSRTransformation ITEM_FIRST_PERSON_RIGHT = create(1.13F, 3.2F, 1.13F, 0.0F, -90.0F, 25.0F, 0.68F);
    public static TRSRTransformation ITEM_FIRST_PERSON_LEFT = create(1.13F, 3.2F, 1.13F, 0.0F, 90.0F, -25.0F, 0.68F);
    public static TRSRTransformation HANDHELD_THIRD_PERSON_RIGHT = create(0.0F, 4.0F, 0.5F, 0.0F, -90.0F, 55.0F, 0.85F);
    public static TRSRTransformation HANDHELD_THIRD_PERSON_LEFT = create(0.0F, 4.0F, 0.5F, 0.0F, 90.0F, -55.0F, 0.85F);
    public static TRSRTransformation BLOCK_GUI = create(0.0F, 0.0F, 0.0F, 30.0F, 225.0F, 0.0F, 0.625F);
    public static TRSRTransformation BLOCK_GROUND = create(0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.25F);
    public static TRSRTransformation BLOCK_FIXED = create(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5F);
    public static TRSRTransformation BLOCK_HEAD = create(0.0F, 13.0F, 7.0F, 0.0F, 180.0F, 0.0F, 1.0F);
    public static TRSRTransformation BLOCK_THIRD_PERSON_RIGHT = create(0.0F, 2.5F, 0.0F, 75.0F, 45.0F, 0.0F, 0.375F);
    public static TRSRTransformation BLOCK_THIRD_PERSON_LEFT = create(0.0F, 2.5F, 0.0F, 75.0F, 45.0F, 0.0F, 0.375F);
    public static TRSRTransformation BLOCK_FIRST_PERSON_RIGHT = create(0.0F, 0.0F, 0.0F, 0.0F, 45.0F, 0.0F, 0.4F);
    public static TRSRTransformation BLOCK_FIRST_PERSON_LEFT = create(0.0F, 0.0F, 0.0F, 0.0F, 225.0F, 0.0F, 0.4F);

    public DefaultTransformations() {
    }

    @Nonnull
    public static TRSRTransformation create(float translateX, float translateY, float translateZ, float rotateX, float rotateY, float rotateZ, float scale) {
        return new TRSRTransformation(new Vector3f(translateX / 16.0F, translateY / 16.0F, translateZ / 16.0F), TRSRTransformation.quatFromXYZDegrees(new Vector3f(rotateX, rotateY, rotateZ)), new Vector3f(scale, scale, scale), (Quat4f)null);
    }

    static {
        itemTransforms.put(ItemCameraTransforms.TransformType.FIXED, ITEM_FIXED);
        itemTransforms.put(ItemCameraTransforms.TransformType.GROUND, ITEM_GROUND);
        itemTransforms.put(ItemCameraTransforms.TransformType.HEAD, ITEM_HEAD);
        itemTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, ITEM_THIRD_PERSON_RIGHT);
        itemTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, ITEM_THIRD_PERSON_LEFT);
        itemTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, ITEM_FIRST_PERSON_RIGHT);
        itemTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, ITEM_FIRST_PERSON_LEFT);
        handheldTransforms.put(ItemCameraTransforms.TransformType.GROUND, ITEM_GROUND);
        handheldTransforms.put(ItemCameraTransforms.TransformType.HEAD, ITEM_HEAD);
        handheldTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HANDHELD_THIRD_PERSON_RIGHT);
        handheldTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HANDHELD_THIRD_PERSON_LEFT);
        handheldTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, ITEM_FIRST_PERSON_RIGHT);
        handheldTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, ITEM_FIRST_PERSON_LEFT);
        blockTransforms.put(ItemCameraTransforms.TransformType.GUI, BLOCK_GUI);
        blockTransforms.put(ItemCameraTransforms.TransformType.GROUND, BLOCK_GROUND);
        blockTransforms.put(ItemCameraTransforms.TransformType.FIXED, BLOCK_FIXED);
        blockTransforms.put(ItemCameraTransforms.TransformType.HEAD, BLOCK_HEAD);
        blockTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, BLOCK_THIRD_PERSON_RIGHT);
        blockTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, BLOCK_THIRD_PERSON_LEFT);
        blockTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, BLOCK_FIRST_PERSON_RIGHT);
        blockTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, BLOCK_FIRST_PERSON_LEFT);
    }
}