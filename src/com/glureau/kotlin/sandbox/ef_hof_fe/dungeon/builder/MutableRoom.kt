package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Door
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Room

/**
 * Interface to modify the room.
 * Created by Greg on 24/01/2016.
 */
interface MutableRoom : Room {

    fun add(item: Item)
    fun add(itemCollection: Collection<Item>)
    fun remove(item: Item): Boolean

    fun add(door: Door)
    fun remove(door: Door): Boolean
}

