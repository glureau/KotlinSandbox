package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.Dungeon
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Door
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Room

/**
 *
 * Created by Greg on 31/01/2016.
 */
class User() {

    private var currentDungeon: Dungeon = Dungeon.NOT_INITIALIZED
    private var currentRoom: Room = Room.NOT_INITIALIZED
    private var inventory: MutableList<Item> = arrayListOf()


    fun startDungeon(dungeon: Dungeon) {
        currentDungeon = dungeon;
        currentRoom = dungeon.startRoom()
    }

    fun hasFinishedCurrentDungeon(): Boolean {
        return currentDungeon.endRoom() == currentRoom
    }

    fun narrateCurrentRoom() {
        println(currentRoom.narrative)
    }

    fun retrieveUserAction() {
        println("User action>")
        var operation = readLine();
        println(operation)
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun finishDungeon() {
        print("Well done! It's finished for today!")
    }

    fun currentRoom(): Room {
        return currentRoom
    }

    fun use(door: Door) {
        door.use(this)
    }

    fun has(item: Item): Boolean {
        return inventory.contains(item)
    }

}