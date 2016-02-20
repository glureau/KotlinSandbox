package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder.DungeonBuilder
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user.User

object Main {
    @JvmStatic fun main(args: Array<String>) {
        val user = User()

        val dungeon = DungeonBuilder({
            val key = Item("key r1<->r2", "A key")
            val r1 = room("room1", "Welcome in the Dark Dungeon", {
                item("black stone", "There is a black stone on the ground")
                item("barrel", "There is a barrel on the ground")
                add(key)
            })
            val r2 = room("room2", "Well done! You're in the room 2", {
                item("yellow stone", "There is a yellow stone on the ground")
            })
            door("big dark door", r1, r2, {roomSrc, roomDest -> user.has(key)})
            startsWith(r1)
            endsWith(r2)
        }).build()

        user.startDungeon(dungeon)
        while (!user.hasFinishedCurrentDungeon()) {
            user.narrateCurrentRoom()
            user.retrieveUserAction()
        }
        user.finishDungeon()
    }

}
