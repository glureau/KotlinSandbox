package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item

/**
 *
 * Created by Greg on 21/02/2016.
 */
class ActionnableItem(val item: Item) : Item by item {

    private var actions: Set<String> = hashSetOf("action", "get", "take")

    fun actions(): Set<String> {
         return actions
    }

    override fun toString(): String {
        return item.toString()
    }
}
