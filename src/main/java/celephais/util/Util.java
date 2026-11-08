package celephais.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {
    public static Random rand = new Random();

    public Util() {
    }

    @Nonnull
    public static <T extends TileEntity> List<T> getTileEntitiesWithin(@Nonnull World world, @Nonnull Class<? extends T> teClass, BlockPos pos, int radius) {
        List<T> tiles = new ArrayList();

        for(int i = pos.getX() - radius; i <= pos.getX() + radius; ++i) {
            for(int j = pos.getY() - radius; j <= pos.getY() + radius; ++j) {
                for(int k = pos.getZ() - radius; k <= pos.getZ() + radius; ++k) {
                    BlockPos p = new BlockPos(i, j, k);
                    Chunk c = world.getChunk(p);
                    if (c.isLoaded()) {
                        TileEntity t = world.getChunk(p).getTileEntity(p, Chunk.EnumCreateEntityType.CHECK);
                        if (t != null && teClass.isInstance(t)) {
                            tiles.add((T) t);
                        }
                    }
                }
            }
        }

        return tiles;
    }

    public static <T extends Entity> List<T> getEntitiesWithinRadius(World world, Class<? extends T> classEntity, BlockPos pos, float xradius, float yradius, float zradius) {
        return world.getEntitiesWithinAABB(classEntity, new AxisAlignedBB((double)((float)pos.getX() - xradius), (double)((float)pos.getY() - yradius), (double)((float)pos.getZ() - zradius), (double)((float)pos.getX() + xradius), (double)((float)pos.getY() + yradius), (double)((float)pos.getZ() + zradius)));
    }

    @Nonnull
    public static String lowercase(@Nonnull String s) {
        String f = "";

        for(int i = 0; i < s.length(); ++i) {
            String c = s.substring(i, i + 1);
            if (c.toUpperCase().compareTo(c) == 0) {
                if (i > 0) {
                    f = f + "_";
                }

                f = f + c.toLowerCase();
            } else {
                f = f + c;
            }
        }

        return f;
    }

    public static int intColor(int r, int g, int b) {
        return -16777216 + r * 65536 + g * 256 + b;
    }

    @Nonnull
    public static String getLowercaseClassName(@Nonnull Class c) {
        String[] nameParts = c.getTypeName().split("\\.");
        String className = nameParts[nameParts.length - 1];
        return lowercase(className);
    }

    public static void spawnInventoryInWorld(World world, double x, double y, double z, IItemHandler inventory) {
        if (inventory != null && !world.isRemote) {
            for(int i = 0; i < inventory.getSlots(); ++i) {
                if (inventory.getStackInSlot(i) != ItemStack.EMPTY) {
                    world.spawnEntity(new EntityItem(world, x, y, z, inventory.getStackInSlot(i)));
                }
            }
        }

    }
}