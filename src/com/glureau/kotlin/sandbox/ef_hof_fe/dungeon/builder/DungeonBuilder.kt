package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.Dungeon
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.*
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.impl.ItemImpl
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.BreakableItem
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.EmbeddableItem
import java.util.*

/**
 * Tooling class to improve the code readability when using this builder.
 * Created by Greg on 24/01/2016.
 */
data class DungeonBuilder(val name: String, val init: DungeonBuilder.() -> Any = {}) {
    private var startRoomBuilder = RoomBuilder.NOT_INITIALIZED
    private var endRoomBuilder = RoomBuilder.NOT_INITIALIZED

    val roomBuilders: MutableSet<RoomBuilder> = hashSetOf()
    val doorBuilders: MutableSet<DoorBuilder> = hashSetOf()

    init {
        init()
    }

    fun startsWith(room: RoomBuilder): DungeonBuilder {
        startRoomBuilder = room
        return this
    }

    fun endsWith(room: RoomBuilder): DungeonBuilder {
        endRoomBuilder = room
        return this
    }

    fun add(room: RoomBuilder): DungeonBuilder {
        roomBuilders.add(room)
        return this
    }

    fun room(narrative: String, init: RoomBuilder.() -> Any = {}): RoomBuilder {
        val room = RoomBuilder(narrative, init)
        roomBuilders.add(room)
        return room
    }

    fun door(narrative: String, roomA: RoomBuilder, roomB: RoomBuilder, dir: Direction, validation: Door.(Room, Room) -> Boolean): DoorBuilder {
        var doorBuilder = DoorBuilder(narrative, roomA, roomB, dir, validation)
        doorBuilders.add(doorBuilder)
        return doorBuilder
    }

    fun embeddableItem(name: String, narrative: String): Item {
        return EmbeddableItem(ItemImpl(name, narrative))
    }

    fun breakableItem(name: String, narrative: String, itemsInside : Collection<Item> = listOf()): Item {
        return BreakableItem(ItemImpl(name, narrative), itemsInside)
    }

    fun item(name: String, narrative: String): Item {
        return ItemImpl(name, narrative)
    }

    fun build(): Dungeon {
        var rooms: MutableSet<Room> = HashSet()
        var startRoom: Room? = null
        var endRoom: Room? = null

        var builderToRoom : MutableMap<RoomBuilder, MutableRoom> = hashMapOf()
        for (roomBuilder in roomBuilders) {
            val room = roomBuilder.build()
            rooms.add(room)
            builderToRoom[roomBuilder] = room
            if (roomBuilder == startRoomBuilder) {
                startRoom = room;
            }
            if (roomBuilder == endRoomBuilder) {
                endRoom = room;
            }
        }

        for (doorBuilder in doorBuilders) {
            doorBuilder.build(builderToRoom);
        }

        if (startRoom != null && endRoom != null) {
            return Dungeon(name, rooms, startRoom, endRoom)
        }
        error("start or end room are not set")
    }
}
