package celephais.events;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public class RegisterContentEvent extends Event {
    private List<Item> items;
    private List<Block> blocks;

    public RegisterContentEvent(List<Item> items, List<Block> blocks) {
        this.items = items;
        this.blocks = blocks;
    }

    public Item addItem(Item item) {
        this.items.add(item);
        return item;
    }

    public Block addBlock(Block block) {
        this.blocks.add(block);
        return block;
    }
}
