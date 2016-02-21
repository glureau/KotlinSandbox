package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item

/**
 * Item you can bring with you, available in the user inventory.
 * Created by Greg on 21/02/2016.
 */
class EmbeddableItem(val item: Item) : Item by item {

    override fun toString(): String {
        return item.toString()
    }
}
