package com.glureau.kotlin.sandbox.dojos.dungeon.user

import com.glureau.kotlin.sandbox.dojos.dungeon.Dungeon
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Door
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Item
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Room
import com.glureau.kotlin.sandbox.dojos.dungeon.interaction.BreakableItem
import com.glureau.kotlin.sandbox.dojos.dungeon.interaction.EmbeddableItem

/**
 * Game progression pointer representing the user.
 *
 * Created by Greg on 31/01/2016.
 */
class User() {

    private var currentDungeon: Dungeon? = null
    private var currentRoom: Room? = null
    var inventory: MutableList<EmbeddableItem> = arrayListOf()


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
        lineSeparation()
        if (inventory.isNotEmpty()) {
            println("Inventory: ${inventory.joinToString(separator = ", ", transform = { it.name })}")
            lineSeparation()
        }

        // this.currentRoom is mutable and we want to avoid unneeded synchronization, so local copy to work on a snapshot.
        var currentRoom: Room = currentRoom ?: return
        println(currentRoom.narrative)
        lineSeparation()
        if (currentRoom.items.isNotEmpty()) {
            println("The room contains:")
            for (item in currentRoom.items) {
                if (item is EmbeddableItem) {
                    println("- ${item.narrative} (${UserAction.TAKE.defaultActionName} ${item.name}?)")
                } else if (item is BreakableItem) {
                    println("- ${item.narrative} (${UserAction.BREAK.defaultActionName} ${item.name}?)")
                } else {
                    println("- ${item.narrative}")
                }
            }
            lineSeparation()
        }
        for (door in currentRoom.doors) {
            println("${door.narrative(this)} (${UserAction.GO.defaultActionName} ${door.directionFrom(currentRoom)}?)")
        }
        if (currentRoom.doors.isNotEmpty()) {
            lineSeparation()
        }
    }

    private fun lineSeparation() {
        println("-------------------------------------------------------")
    }

    fun retrieveUserAction() {
        print("Action> ")
        var operation = readLine();
        if (operation != null) {
            var input = CommandInterpreter().prepare(operation)
            if (input.act(this)) {
                println("Result> Done ${input.verb}")
            } else {
                println("Result> I don't understand, please retry with a valid command")
            }
        }
    }

    fun finishDungeon() {
        print("Well done! It's finished for today!")
    }

    fun take(item: EmbeddableItem) {
        inventory.add(item)
    }

    fun drop(item: EmbeddableItem): Boolean {
        return inventory.remove(item)
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