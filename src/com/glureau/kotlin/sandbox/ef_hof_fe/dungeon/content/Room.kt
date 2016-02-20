package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content

/**
 *
 * Created by Greg on 24/01/2016.
 */
data class Room(val name: String = "unnamed", val narrative: String = "undefined", val init: Room.() -> Any = {}) {
    companion object {
        val NOT_INITIALIZED = Room()
    }

    val items: MutableList<Item>

    init {
        items = arrayListOf()
        init()
    }

    fun item(name: String, narrative: String): Item {
        var item = Item(name, narrative)
        items.add(item)
        return item
    }

    fun add(item: Item): Room {
        items.add(item)
        return this
    }
}

