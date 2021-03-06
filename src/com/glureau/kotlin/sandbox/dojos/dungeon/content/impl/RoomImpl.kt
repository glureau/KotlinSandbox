package com.glureau.kotlin.sandbox.dojos.dungeon.content.impl

import com.glureau.kotlin.sandbox.dojos.dungeon.builder.MutableRoom
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Door
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Item

/**
 *
 * Created by Greg on 24/01/2016.
 */
data class RoomImpl(
        override val narrative: String,
        override val items: MutableList<Item> = arrayListOf(),
        override val doors: MutableList<Door> = arrayListOf()
) : MutableRoom {
    override fun add(item: Item) {
        items.add(item)
    }

    override fun add(itemCollection: Collection<Item>) {
        items.addAll(itemCollection)
    }

    override fun remove(item: Item): Boolean {
        return items.remove(item)
    }

    override fun add(door: Door) {
        doors.add(door)
    }

    override fun remove(door: Door): Boolean {
        return doors.remove(door)
    }
}

