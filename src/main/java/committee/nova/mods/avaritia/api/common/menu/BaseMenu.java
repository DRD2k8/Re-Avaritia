package committee.nova.mods.avaritia.api.common.menu;

import committee.nova.mods.avaritia.api.common.tile.BaseTileEntity;
import committee.nova.mods.avaritia.util.TileEntityUtil;
import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import lombok.Getter;
import net.fabricmc.api.EnvType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 19:46
 * Version: 1.0
 */
public abstract class BaseMenu<T extends BaseTileEntity> extends AbstractContainerMenu {

    public static final int PLAYERSIZE = 4 * 9;
    protected Player playerEntity;
    protected Inventory playerInventory;
    protected int startInv = 0;
    protected int endInv = 17; //must be set by extending class
    @Getter
    private final T tileEntity;

    protected BaseMenu(@Nullable MenuType<?> pMenuType, int pContainerId, T tileEntity) {
        super(pMenuType, pContainerId);
        this.tileEntity = tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return true;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        try {
            //if last machine slot is 17, endInv is 18
            int playerStart = endInv;
            int playerEnd = endInv + PLAYERSIZE; //53 = 17 + 36
            //standard logic based on start/end
            ItemStack itemstack = ItemStack.EMPTY;
            Slot slot = this.slots.get(index);
            if (slot != null && slot.hasItem()) {
                ItemStack stack = slot.getItem();
                itemstack = stack.copy();
                if (index < this.endInv) {
                    if (!this.moveItemStackTo(stack, playerStart, playerEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index <= playerEnd && !this.moveItemStackTo(stack, startInv, endInv, false)) {
                    return ItemStack.EMPTY;
                }
                if (stack.isEmpty()) {
                    slot.set(ItemStack.EMPTY);
                } else {
                    slot.setChanged();
                }
                if (stack.getCount() == itemstack.getCount()) {
                    return ItemStack.EMPTY;
                }
                slot.onTake(player, stack);
            }
            return itemstack;
        } catch (Exception e) {
            return ItemStack.EMPTY;
        }
    }

    private int addSlotRange(Inventory handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(Inventory handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @Nullable
    public static <TILE extends BlockEntity> TILE getTileEntityFromBuf(@Nullable FriendlyByteBuf buf, Class<TILE> type) {
        if (buf == null) {
            return null;
        }
        return EnvExecutor.callWhenOn(EnvType.CLIENT, () -> () -> TileEntityUtil.get(type, Minecraft.getInstance().level, buf.readBlockPos()).orElse(null));
    }

}
