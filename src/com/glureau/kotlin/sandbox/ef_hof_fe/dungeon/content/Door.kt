package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user.User

/**
 *
 * Created by Greg on 20/02/2016.
 */
class Door(val narrative: String = "undefined", val left: Room, val right: Room, val validation: Door.(roomSrc: Room, roomDest: Room) -> Boolean = { src, dest -> true }) {

    @Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER")
    fun use(user: User): Room {

        var roomSrc = user.currentRoom()

        // Determine the destination room
        var roomDest = Room.NOT_INITIALIZED
        if (roomSrc == left) {
            roomDest = right
        } else if (roomSrc == right) {
            roomDest = left
        } else {
            throw IllegalStateException("Door is not available in this room")
        }

        if (validation(roomSrc, roomDest)) {
            // Success
            return roomDest
        }
        // Failure
        return roomSrc
    }
}