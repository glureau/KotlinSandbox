package com.glureau.kotlin.sandbox.dojos.dungeon.user

import com.glureau.kotlin.sandbox.dojos.dungeon.builder.MutableRoom
import com.glureau.kotlin.sandbox.dojos.dungeon.content.Direction
import com.glureau.kotlin.sandbox.dojos.dungeon.interaction.BreakerItem

/**
 * All actions a user can do
 * Created by Greg on 21/02/2016.
 */
enum class UserAction(val defaultActionName: String, val actionNames: Set<String>, private val action: UserAction.(user: User, input: UserInput) -> Unit) {
    TAKE("take", hashSetOf("take", "get", "acquire", "catch", "pick"), { user, input -> take(user, input) }),
    DROP("drop", hashSetOf("drop", "release", "let", "blurt"), { user, input -> drop(user, input) }),
    GO("go", hashSetOf("go", "move", "walk", "run", "travel", "proceed", "shift"), { user, input -> move(user, input) }),
    BREAK("break", hashSetOf("break", "crack", "snap", "quash"), { user, input -> crack(user, input) });

    fun act(user: User, input: UserInput) = action(user, input)

    private fun take(user: User, input: UserInput) {
        val item = input.embeddableItemInRoom(user) ?: badInput("No embeddable item found in the room for '${input.directObject}'")
        val room = user.currentRoom() ?: badInput("User is not in a room");
        if (room is MutableRoom && room.remove(item)) {
            user.take(item)
        } else {
            badInput("Cannot take the item $item")
        }
    }

    private fun drop(user: User, input: UserInput) {
        val item = input.embeddableItemInInventory(user) ?: badInput("No item in the inventory found for '${input.directObject}'")
        val room = user.currentRoom();
        if (room is MutableRoom && user.drop(item)) {
            room.add(item)
        } else {
            badInput("Cannot drop the item $item")
        }
    }

    private fun move(user: User, input: UserInput) {
        val room = user.currentRoom() ?: badInput("User is not in a room");
        val dir = input.direction() ?: badInput("${input.directObject} is not a known Direction (${Direction.values().joinToString(separator = ", ", transform = { it.name })})")
        val doors = room.doors.filter { (it.dir == dir && it.left == room) || (it.dir.opposite() == dir && it.right == room) }
        if (doors.isEmpty()) badInput("No door in this direction")
        if (doors.size > 1) badInput("Too much doors in this direction")
        if (!user.use(doors.single())) badInput("The door is closed.")
    }

    private fun crack(user: User, input: UserInput) {
        val room = user.currentRoom()
        // Requires at least one breaker object to break something.
        val breakerItem = user.inventory.firstOrNull { it is BreakerItem } ?: badInput("You need to have an object in your inventory in order to break something")
        val item = input.breakableItem(user) ?: badInput("No breakable object found for '${input.directObject}'")
        if (room !is MutableRoom || !room.remove(item)) badInput("Cannot break ${item.name} with ${breakerItem.name}")
        room.add(item.inside())
    }

    private fun badInput(msg: String): Nothing {
        throw BadUserInputException(msg);
    }
}
