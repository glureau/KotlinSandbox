package com.glureau.kotlin.sandbox.dojos.dungeon.user

import com.glureau.kotlin.sandbox.dojos.dungeon.Dungeon
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Door
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Item
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Room
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
    private val narrator = Narrator()


    fun startDungeon(dungeon: Dungeon) {
        currentDungeon = dungeon;
        currentRoom = dungeon.startRoom()
        narrator.startDungeon(dungeon)
    }

    fun hasFinishedCurrentDungeon(): Boolean {
        return currentDungeon?.endRoom() == currentRoom
    }

    fun narrate() {
        narrator.narrate(this)
    }

    fun retrieveUserAction() {
        narrator.waitUserInput()
        var operation = readLine();
        if (operation != null) {
            var input = UserInput.prepare(operation)
            var badUserInputException: BadUserInputException? = null;
            try {
                input.act(this)
            } catch(e: BadUserInputException) {
                badUserInputException = e
            }
            narrator.inputValidation(input, badUserInputException)
        }
    }

    fun finishDungeon() {
        narrator.finishDungeon()
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