package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user.User

/**
 *
 * Created by Greg on 20/02/2016.
 * @param dir ordinal direction from the left room to the right room.
 */
class Door(override val narrative: String = "undefined", val left: Room, val right: Room, val dir: Direction, val validation: Door.(roomSrc: Room, roomDest: Room) -> Boolean) : Item {
    override val name ="not used"

    fun narrative(user: User): String {
        val currentRoom = user.currentRoom()
        var d: Direction? = directionFrom(currentRoom)
        return "In the $d of the room, you can see $narrative"
    }

    fun directionFrom(currentRoom: Room?): Direction? {
        var d: Direction?
        if (currentRoom == left) {
            d = dir
        } else if (currentRoom == right) {
            d = dir.opposite()
        } else {
            error("Door is not available in this room")
        }
        return d
    }

    fun use(user: User): Room {

        var roomSrc: Room? = user.currentRoom()

        // Determine the destination room
        var roomDest: Room?
        if (roomSrc == left) {
            roomDest = right
        } else if (roomSrc == right) {
            roomDest = left
        } else {
            error("Door is not available in this room")
        }

        if (validation(roomSrc, roomDest)) {
            // Success
            return roomDest
        }

        // Failure
        return roomSrc
    }
}
