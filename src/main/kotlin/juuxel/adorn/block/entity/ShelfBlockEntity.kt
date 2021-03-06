package juuxel.adorn.block.entity

import juuxel.adorn.block.AdornBlockEntities
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.nbt.CompoundTag

class ShelfBlockEntity : BaseInventoryBlockEntity(AdornBlockEntities.SHELF, 2), BlockEntityClientSerializable {
    // No menus for shelves
    override fun createContainer(syncId: Int, playerInv: PlayerInventory?) = null

    override fun getInvMaxStackAmount() = 1

    override fun toClientTag(tag: CompoundTag) = tag.apply {
        Inventories.toTag(tag, items)
    }

    override fun fromClientTag(tag: CompoundTag) {
        Inventories.fromTag(tag, items)
    }
}
