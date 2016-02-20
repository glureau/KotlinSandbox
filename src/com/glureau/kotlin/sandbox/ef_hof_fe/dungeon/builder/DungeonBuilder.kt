package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.Dungeon
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Door
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Room

/**
 *
 * Created by Greg on 24/01/2016.
 */
data class DungeonBuilder(val init: DungeonBuilder.() -> Any = {}) {
    private var startRoom: Room = Room.NOT_INITIALIZED
    private var endRoom: Room = Room.NOT_INITIALIZED

    val rooms: MutableSet<Room>
    init {
        rooms = hashSetOf()
        init()
    }

    fun startsWith(room: Room): DungeonBuilder {
        startRoom = room
        return this
    }

    fun endsWith(room: Room): DungeonBuilder {
        endRoom = room
        return this
    }

    fun add(room: Room): DungeonBuilder {
        rooms.add(room)
        return this
    }

    fun room(name: String, narrative: String, init: Room.() -> Any = {}): Room {
        val room = Room(name, narrative, init)
        rooms.add(room)
        return room
    }

    fun door(narrative: String, roomA: Room, roomB: Room, validation: Door.(Room, Room) -> Boolean): Door {
        var door = Door(narrative, roomA, roomB, validation)
        return door
    }

    fun build(): Dungeon {
        print(this)
        return Dungeon(rooms, startRoom, endRoom)
    }
}
