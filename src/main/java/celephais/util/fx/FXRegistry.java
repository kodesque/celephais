package celephais.util.fx;

import celephais.network.proxy.ClientProxy;
import celephais.root.Main;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FXRegistry {
    public static Map<Integer, Function<NBTTagCompound, Void>> effects = new HashMap();
    private static int id = 0;

    public FXRegistry() {
    }

    public static int registerEffect(@Nonnull Function<NBTTagCompound, Void> func) {
        if (Main.proxy instanceof ClientProxy) {
            effects.put(id, func);
        }

        return id++;
    }
}
