package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Room

/**
 *
 * Created by Greg on 24/01/2016.
 */
class Dungeon(val name: String, val rooms: Set<Room>, val startRoom: Room, val endRoom: Room) {

    fun startRoom(): Room {
        return startRoom
    }

    fun endRoom(): Room {
        return endRoom
    }
}
