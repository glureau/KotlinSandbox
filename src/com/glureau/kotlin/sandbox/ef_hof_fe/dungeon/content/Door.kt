package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user.User

/**
 *
 * Created by Greg on 20/02/2016.
 * @param dir ordinal direction from the left room to the right room.
 */
class Door(val narrative: String = "undefined", val left: Room, val right: Room, val dir: Direction, val validation: Door.(roomSrc: Room, roomDest: Room) -> Boolean = { src, dest -> true }) : Item {
    override fun name(): String {
        return "not used"
    }

    fun narrative(user: User): String {
        var d: Direction? = null
        if (user.currentRoom() == left) {
            d = dir
        } else if (user.currentRoom() == right) {
            d = dir.opposite()
        } else {
            error("Door not available in the user current room")
        }
        return "In the $d of the room, you can see $narrative"
    }

    fun use(user: User): Room {

        var roomSrc: Room? = user.currentRoom()

        // Determine the destination room
        var roomDest: Room? = null
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
