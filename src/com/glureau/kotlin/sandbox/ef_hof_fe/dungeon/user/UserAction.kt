package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder.MutableRoom
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.EmbeddableItem

/**
 * All actions a user can do
 * Created by Greg on 21/02/2016.
 */
enum class UserAction(val defaultActionName: String, val actionNames: Set<String>, private val action: UserAction.(user: User, input: UserInput) -> Boolean) {
    TAKE("take", hashSetOf("take", "get"), { user, input -> take(user, input) }),
    DROP("drop", hashSetOf("drop"), { user, input -> drop(user, input) }),
    MOVE("move", hashSetOf("move", "go", "walk", "run"), { user, input -> move(user, input)});

    fun act(user: User, input: UserInput): Boolean = action(user, input)

    private fun take(user: User, input: UserInput): Boolean {
        val item= input.embeddableItem(user)
        val room = user.currentRoom();
        if (item != null && item is EmbeddableItem && room != null && room is MutableRoom && room.remove(item)) {
            user.take(item)
            return true
        }
        return false
    }

    private fun drop(user: User, input: UserInput): Boolean {
        val item = input.embeddableItem(user)
        val room = user.currentRoom();
        if (item != null && room is MutableRoom && user.drop(item)) {
            room.add(item)
            return true
        }
        return false
    }

    private fun move(user: User, input: UserInput): Boolean {
        val room = user.currentRoom();
        val dir = input.direction()
        val door = room?.doors?.filter { (it.dir == dir && it.left == room) || (it.dir.opposite() == dir && it.right == room) }?.singleOrNull()
        if (door != null) {
            user.use(door)
            return true;
        }
        println("Too much doors or no door at all in this direction")
        return false
    }
}
