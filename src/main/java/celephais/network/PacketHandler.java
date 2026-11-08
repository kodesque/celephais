package celephais.network;

import celephais.util.MessageEffect;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static SimpleNetworkWrapper INSTANCE;
    private static int id;

    public PacketHandler() {
    }

    public static void registerMessages() {
        INSTANCE.registerMessage(MessageEffect.MessageHolder.class, MessageEffect.class, id++, Side.CLIENT);
    }

    public static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> message, Side side) {
        INSTANCE.registerMessage(handler, message, id++, side);
    }

    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("celephais");
        id = 0;
    }
}