package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.impl.ItemImpl
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.impl.RoomImpl
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.BreakableItem
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.BreakerItem
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.EmbeddableItem

/**
 *
 * Created by Greg on 24/01/2016.
 */
data class RoomBuilder(val narrative: String = "undefined", val init: RoomBuilder.() -> Any = {}) {
    companion object {
        val NOT_INITIALIZED = RoomBuilder()
    }

    val items: MutableList<Item> = arrayListOf()
    val doors: MutableList<DoorBuilder> = arrayListOf()

    init {
        init()
    }

    fun item(name: String, narrative: String): Item {
        var item = ItemImpl(name, narrative)
        items.add(item)
        return item
    }

    fun embeddableItem(name: String, narrative: String): EmbeddableItem {
        var item = EmbeddableItem(ItemImpl(name, narrative))
        items.add(item)
        return item
    }

    fun breakerItem(name: String, narrative: String): BreakerItem {
        var item = BreakerItem(ItemImpl(name, narrative))
        items.add(item)
        return item
    }

    fun breakableItem(name: String, narrative: String, itemsInside : Collection<Item> = listOf()): BreakableItem {
        var item = BreakableItem(ItemImpl(name, narrative), itemsInside)
        items.add(item)
        return item
    }

    fun hiddenEmbeddableItem(name: String, narrative: String): Item {
        return EmbeddableItem(ItemImpl(name, narrative))
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
        return RoomImpl(narrative, items)
    }
}

