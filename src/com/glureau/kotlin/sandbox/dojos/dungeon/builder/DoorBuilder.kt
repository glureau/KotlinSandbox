package com.glureau.kotlin.sandbox.dojos.dungeon.builder

import com.glureau.kotlin.sandbox.dojos.dungeon.content.Direction
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Door
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Room
import com.glureau.kotlin.sandbox.dojos.dungeon.content.impl.RoomImpl

/**
 *
 * Created by Greg on 20/02/2016.
 * @param dir ordinal direction from the left room to the right room.
 */
class DoorBuilder(val narrative: String = "undefined", val leftBuilder: RoomBuilder, val rightBuilder: RoomBuilder, val dir: Direction, val validation: Door.(roomSrc: Room, roomDest: Room) -> Boolean) {

    fun build(rooms: Map<RoomBuilder, MutableRoom>): Door {
        if (rooms.contains(leftBuilder) && rooms.contains(rightBuilder)) {
            val left = rooms.getOrElse(leftBuilder, {null})
            val right = rooms.getOrElse(rightBuilder, {null})
            if (left == null || right == null) error("Left and Right should not be null")
            val door = Door(narrative, left, right, dir, validation)
            left.add(door)
            right.add(door)
            return door
        }
        error("One room is missing, cannot create the door")
    }

}
