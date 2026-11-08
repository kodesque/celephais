package celephais.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class MessageLeftClickEmpty implements IMessage {
    public UUID id = null;
    public EnumHand hand = null;
    public NBTTagCompound tag = new NBTTagCompound();

    public MessageLeftClickEmpty() {
    }

    public MessageLeftClickEmpty(EntityPlayer player, EnumHand hand, ItemStack stack) {
        this.id = player.getUniqueID();
        this.hand = hand;
        this.tag = stack.writeToNBT(new NBTTagCompound());
    }

    public void fromBytes(ByteBuf buf) {
        this.id = new UUID(buf.readLong(), buf.readLong());
        this.hand = buf.readBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
        this.tag = ByteBufUtils.readTag(buf);
    }

    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.id.getMostSignificantBits());
        buf.writeLong(this.id.getLeastSignificantBits());
        buf.writeBoolean(this.hand == EnumHand.MAIN_HAND);
        ByteBufUtils.writeTag(buf, this.tag);
    }

    public static class MessageHolder implements IMessageHandler<MessageLeftClickEmpty, IMessage> {
        public MessageHolder() {
        }

        public IMessage onMessage(MessageLeftClickEmpty message, MessageContext ctx) {
            MinecraftForge.EVENT_BUS.post(new PlayerServerInteractEvent.LeftClickAir(ctx.getServerHandler().player.world.getPlayerEntityByUUID(message.id), message.hand, new ItemStack(message.tag)));
            return null;
        }
    }
}
