package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder.DungeonBuilder
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Direction
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user.User

object Main {
    @JvmStatic fun main(args: Array<String>) {
        val user = User()

        val dungeon = DungeonBuilder("Dark Dungeon", {
            val key = item("key", "A key")
            val r1 = room("You are in a large corridor of gray stones, covered with mold.", {
                item("black stone", "There is a black stone on the ground")
                item("barrel", "There is a barrel on the ground")
                add(key)
            })
            val r2 = room("You entered in a great room with white and black stones everywhere.", {
                item("yellow stone", "There is a yellow stone on the ground")
            })
            door("a big dark door", r1, r2, Direction.NORTH, { roomSrc, roomDest -> user.has(key)})

            val endRoom = room("Well done! You're in the last room of this dungeon.")
            door("THE last door", r2, endRoom, Direction.EAST, { roomSrc, roomDest -> user.has(key)})

            startsWith(r1)
            endsWith(endRoom)
        }).build()

        user.startDungeon(dungeon)
        while (!user.hasFinishedCurrentDungeon()) {
            user.narrate()
            user.retrieveUserAction()
        }
        user.finishDungeon()
    }

}
