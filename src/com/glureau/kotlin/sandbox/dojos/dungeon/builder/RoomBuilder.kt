package com.glureau.kotlin.sandbox.dojos.dungeon.builder

import com.glureau.kotlin.sandbox.dojos.dungeon.content.Item
import com.glureau.kotlin.sandbox.dojos.dungeon.content.impl.ItemImpl
import com.glureau.kotlin.sandbox.dojos.dungeon.content.impl.RoomImpl
import com.glureau.kotlin.sandbox.dojos.dungeon.interaction.BreakableItem
import com.glureau.kotlin.sandbox.dojos.dungeon.interaction.BreakerItem
import com.glureau.kotlin.sandbox.dojos.dungeon.interaction.EmbeddableItem

/**
 *
 * Created by Greg on 24/01/2016.
 */
data class RoomBuilder(val narrative: String, val init: RoomBuilder.() -> Any = {}) {
    companion object {
        val NOT_INITIALIZED = RoomBuilder("undefined")
    }

    val items: MutableList<Item> = arrayListOf()
    val doors: MutableList<DoorBuilder> = arrayListOf()

    init {
        init()
    }

    fun item(name: String, narrative: String): Item {
        var item = hiddenItem(name, narrative)
        items.add(item)
        return item
    }

    fun hiddenItem(name: String, narrative: String): Item {
        return ItemImpl(name, narrative)
    }

    fun embeddableItem(name: String, narrative: String): EmbeddableItem {
        var item = hiddenEmbeddableItem(name, narrative)
        items.add(item)
        return item
    }

    fun hiddenEmbeddableItem(name: String, narrative: String): EmbeddableItem {
        return EmbeddableItem(ItemImpl(name, narrative))
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

