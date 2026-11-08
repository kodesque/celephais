package celephais.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

public class PlayerServerInteractEvent extends Event {
    @Nonnull
    protected EntityPlayer player;
    @Nonnull
    protected EnumHand hand;
    @Nonnull
    protected ItemStack stack;

    public PlayerServerInteractEvent(@Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull ItemStack stack) {
        this.player = player;
        this.hand = hand;
        this.stack = stack;
    }

    @Nonnull
    public EntityPlayer getPlayer() {
        return this.player;
    }

    @Nonnull
    public EnumHand getHand() {
        return this.hand;
    }

    @Nonnull
    public ItemStack getStack() {
        return this.stack;
    }

    public static class LeftClickAir extends PlayerServerInteractEvent {
        public LeftClickAir(@Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull ItemStack stack) {
            super(player, hand, stack);
        }
    }

    public static class RightClickAir extends PlayerServerInteractEvent {
        public RightClickAir(@Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull ItemStack stack) {
            super(player, hand, stack);
        }
    }
}
