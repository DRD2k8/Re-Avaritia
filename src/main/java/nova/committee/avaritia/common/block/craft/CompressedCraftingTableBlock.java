package nova.committee.avaritia.common.block.craft;

import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/1 21:44
 * Version: 1.0
 */
public class CompressedCraftingTableBlock extends CraftingTableBlock {
    public CompressedCraftingTableBlock() {
        super(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD));
        setRegistryName("compressed_crafting_table");
    }

}
