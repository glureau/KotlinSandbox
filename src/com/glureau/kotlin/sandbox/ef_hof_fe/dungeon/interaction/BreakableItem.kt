package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item

/**
 * Item you can break, available in the user inventory.
 * Created by Greg on 23/02/2016.
 */
class BreakableItem(val item: Item, val itemsInside : Collection<Item>) : Item by item {

    override fun toString(): String {
        return item.toString()
    }

    fun inside(): Collection<Item> {
        return itemsInside
    }
}
