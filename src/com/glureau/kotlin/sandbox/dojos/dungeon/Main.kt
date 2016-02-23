package com.glureau.kotlin.sandbox.dojos.dungeon

import com.glureau.kotlin.sandbox.dojos.dungeon.builder.DungeonBuilder
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Direction
import com.glureau.kotlin.sandbox.dojos.dungeon.user.User

object Main {
    @JvmStatic fun main(args: Array<String>) {
        val user = User()

        val dungeon = DungeonBuilder("Dark Dungeon", {
            val key = embeddableItem("key", "A key")
            val startRoom = room("You are in a large corridor of gray stones, covered with mold.", {
                breakerItem("black stone", "a black stone on the ground")
                breakableItem("barrel", "a barrel with some mysterious engraving on it", listOf(
                        key,
                        hiddenItem("paper", "an old manuscript in the barrel rubble where is written: 'this is THE key'")))
            })
            val middleRoom = room("You entered in a huge room with white and black stones everywhere.", {
                item("yellow stone", "There is a yellow stone on the ground")
            })
            val endRoom = room()

            door("a large wood door", startRoom, middleRoom, Direction.NORTH, { roomSrc, roomDest -> user.has(key) })
            door("THE last door (you cannot bring your stuff to travel)", middleRoom, endRoom, Direction.EAST, { roomSrc, roomDest -> user.inventory.isEmpty() })

            startsWith(startRoom)
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
