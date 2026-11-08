package celephais.util;

import celephais.client.fx.FXRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Function;

public class MessageEffect implements IMessage {
    public int id = 0;
    public NBTTagCompound tag = new NBTTagCompound();

    public MessageEffect() {
    }

    public MessageEffect(int id, NBTTagCompound tag) {
        this.tag = tag;
        this.id = id;
    }

    public void fromBytes(ByteBuf buf) {
        this.id = buf.readInt();
        this.tag = ByteBufUtils.readTag(buf);
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.id);
        ByteBufUtils.writeTag(buf, this.tag);
    }

    public static class MessageHolder implements IMessageHandler<MessageEffect, IMessage> {
        public MessageHolder() {
        }

        @SideOnly(Side.CLIENT)
        public IMessage onMessage(MessageEffect message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> ((Function) FXRegistry.effects.get(message.id)).apply(message.tag));
            return null;
        }
    }
}
