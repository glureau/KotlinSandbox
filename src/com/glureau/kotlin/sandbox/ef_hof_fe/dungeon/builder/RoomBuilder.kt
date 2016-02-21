package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.impl.ItemImpl
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.impl.RoomImpl
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.EmbeddableItem

/**
 *
 * Created by Greg on 24/01/2016.
 */
data class RoomBuilder(val name: String = "unnamed", val narrative: String = "undefined", val init: RoomBuilder.() -> Any = {}) {
    companion object {
        val NOT_INITIALIZED = RoomBuilder()
    }

    val items: MutableList<Item> = arrayListOf()
    val doors: MutableList<DoorBuilder> = arrayListOf()

    init {
        init()
    }

    fun item(name: String, narrative: String): Item {
        var item = EmbeddableItem(ItemImpl(name, narrative))
        items.add(item)
        return item
    }

    fun add(door: DoorBuilder): RoomBuilder {
        doors.add(door)
        return this
    }

    fun add(item: Item): RoomBuilder {
        items.add(item)
        return this
    }

    fun build(): MutableRoom {
        return RoomImpl(name, narrative, items)
    }
}

