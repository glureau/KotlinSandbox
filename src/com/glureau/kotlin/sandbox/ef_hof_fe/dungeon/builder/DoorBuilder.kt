package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Direction
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Door
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Room
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.impl.RoomImpl

/**
 *
 * Created by Greg on 20/02/2016.
 * @param dir ordinal direction from the left room to the right room.
 */
class DoorBuilder(val narrative: String = "undefined", val leftBuilder: RoomBuilder, val rightBuilder: RoomBuilder, val dir: Direction, val validation: Door.(roomSrc: Room, roomDest: Room) -> Boolean = { src, dest -> true }) {

    fun build(rooms: Map<RoomBuilder, MutableRoom>): Door {
        if (rooms.contains(leftBuilder) && rooms.contains(rightBuilder)) {
            val left = rooms.getOrImplicitDefault(leftBuilder)
            val right = rooms.getOrImplicitDefault(rightBuilder)
            val door = Door(narrative, left, right, dir, validation)
            left.add(door)
            right.add(door)
            return door
        }
        error("One room is missing, cannot create the door")
    }

}
