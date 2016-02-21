package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.Dungeon
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Door
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Room
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.ActionnableItem

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
        println("Welcome in the ${dungeon.name}")
    }

    fun hasFinishedCurrentDungeon(): Boolean {
        return currentDungeon.endRoom() == currentRoom
    }

    fun narrate() {
        println()
        println("-----------------------------------")
        if (inventory.isNotEmpty()) {
            println("Inventory: ${inventory.joinToString(separator = ",", transform= { it.name() })}")
        }
        println(currentRoom.narrative)
        println("The room contains:")
        for (item in currentRoom.items) {
            if (item is ActionnableItem) {
                println("- ${item.name()} (${item.actions()})")
            } else {
                println("- ${item.name()}")
            }
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

    fun take(item: Item) {
        inventory.add(item)
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