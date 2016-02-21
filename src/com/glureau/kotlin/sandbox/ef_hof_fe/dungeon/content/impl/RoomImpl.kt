package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.impl

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Door
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder.MutableRoom

/**
 *
 * Created by Greg on 24/01/2016.
 */
data class RoomImpl(
        override val name: String,
        override val narrative: String,
        override val items: MutableList<Item> = arrayListOf(),
        override val doors: MutableList<Door> = arrayListOf()
) : MutableRoom {

    override fun add(item: Item) {
        items.add(item)
    }

    override fun remove(item: Item) {
        items.remove(item)
    }

    override fun add(door: Door) {
        doors.add(door)
    }

    override fun remove(door: Door) {
        doors.remove(door)
    }
}

