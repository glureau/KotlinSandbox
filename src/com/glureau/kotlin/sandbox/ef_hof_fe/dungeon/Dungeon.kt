package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Room

/**
 *
 * Created by Greg on 24/01/2016.
 */
public class Dungeon(val name: String, val rooms: Set<Room>, val startRoom: Room, val endRoom: Room) {
    companion object {
        val NOT_INITIALIZED = Dungeon("", setOf(Room.NOT_INITIALIZED), Room.NOT_INITIALIZED, Room.NOT_INITIALIZED)
    }

    init {
        if (!rooms.contains(startRoom)) {
            if (startRoom == Room.NOT_INITIALIZED) {
                throw IllegalArgumentException("startRoom should be set")
            } else {
                throw IllegalArgumentException("startRoom should be an existing room in this dungeon.")
            }
        }
        if (!rooms.contains(endRoom)) {
            if (endRoom == Room.NOT_INITIALIZED) {
                throw IllegalArgumentException("startRoom should be set")
            } else {
                throw IllegalArgumentException("endRoom should be an existing room in this dungeon.")
            }
        }
    }

    fun startRoom(): Room {
        return startRoom
    }

    fun endRoom(): Room {
        return endRoom
    }
}
