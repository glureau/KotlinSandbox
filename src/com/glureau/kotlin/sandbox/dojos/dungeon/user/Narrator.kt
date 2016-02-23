package com.glureau.kotlin.sandbox.dojos.dungeon.user

import com.glureau.kotlin.sandbox.dojos.dungeon.Dungeon
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Room
import com.glureau.kotlin.sandbox.dojos.dungeon.interaction.BreakableItem
import com.glureau.kotlin.sandbox.dojos.dungeon.interaction.EmbeddableItem

/**
 * Manage the game narration.
 * Created by Greg on 31/01/2016.
 */
class Narrator() {

    fun startDungeon(dungeon: Dungeon) = println("Welcome in the ${dungeon.name}")
    fun finishDungeon() = println("Well done! It's finished for today!")

    fun narrate(user: User) {
        println()
        lineSeparation()
        if (user.inventory.isNotEmpty()) {
            println("Inventory: ${user.inventory.joinToString(separator = ", ", transform = { it.name })}")
            lineSeparation()
        }

        // this.currentRoom is mutable and we want to avoid unneeded synchronization, so local copy to work on a snapshot.
        var currentRoom: Room = user.currentRoom() ?: return
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

        if (currentRoom.doors.isNotEmpty()) {
            for (door in currentRoom.doors) {
                println("${door.narrative(user)} (${UserAction.GO.defaultActionName} ${door.directionFrom(currentRoom)}?)")
            }
            lineSeparation()
        }
    }

    private fun lineSeparation() = println("-".repeat(100))

    fun waitUserInput() = print("Action> ")
    fun inputValidation(input: UserInput, badUserInput: BadUserInputException?) {
        if (badUserInput == null) {
            println("Result> Done ${input.verb}")
        } else {
            println("Result> ${badUserInput.message}")
        }
    }

}