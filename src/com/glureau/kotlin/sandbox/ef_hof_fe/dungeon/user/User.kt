package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.Dungeon
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Door
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Room
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.EmbeddableItem

/**
 *
 * Created by Greg on 31/01/2016.
 */
class User() {

    private var currentDungeon: Dungeon? = null
    private var currentRoom: Room? = null
    private var inventory: MutableList<EmbeddableItem> = arrayListOf()


    fun startDungeon(dungeon: Dungeon) {
        currentDungeon = dungeon;
        currentRoom = dungeon.startRoom()
        println("Welcome in the ${dungeon.name}")
    }

    fun hasFinishedCurrentDungeon(): Boolean {
        return currentDungeon?.endRoom() == currentRoom
    }

    fun narrate() {
        println()
        println("-----------------------------------")
        if (inventory.isNotEmpty()) {
            println("Inventory: ${inventory.joinToString(separator = ",", transform = { it.name() })}")
        }

        // this.currentRoom is mutable and we want to avoid unneeded synchronization, so local copy to work on a snapshot.
        var currentRoom: Room = currentRoom ?: return
        println(currentRoom.narrative)
        if (currentRoom.items.isNotEmpty()) {
            println("The room contains:")
            for (item in currentRoom.items) {
                if (item is EmbeddableItem) {
                    println("- ${item.name()} (${UserAction.TAKE.defaultAction} ?)")
                } else {
                    println("- ${item.name()}")
                }
            }
        }
        for (door in currentRoom.doors) {
            println(door.narrative(this))
        }
    }

    fun retrieveUserAction() {
        println("User action>")
        var operation = readLine();
        var operationInterpreted = false;
        if (operation != null) {
            operationInterpreted = CommandInterpreter().interpret(this, operation)
        }

        if (!operationInterpreted) {
            println("Please try a valid command")
        }
    }

    fun finishDungeon() {
        print("Well done! It's finished for today!")
    }

    fun take(item: EmbeddableItem) {
        inventory.add(item)
    }

    fun currentRoom(): Room? {
        return currentRoom
    }

    fun use(door: Door): Boolean {
        val newRoom = door.use(this)
        if (newRoom != currentRoom) {
            currentRoom = newRoom
            return true
        }
        return false
    }

    fun has(item: Item): Boolean {
        return inventory.contains(item)
    }

}