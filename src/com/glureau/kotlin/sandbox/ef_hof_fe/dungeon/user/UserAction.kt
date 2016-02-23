package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder.MutableRoom
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.BreakerItem
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.EmbeddableItem

/**
 * All actions a user can do
 * Created by Greg on 21/02/2016.
 */
enum class UserAction(val defaultActionName: String, val actionNames: Set<String>, private val action: UserAction.(user: User, input: UserInput) -> Boolean) {
    TAKE("take", hashSetOf("take", "get", "acquire", "catch", "pick"), { user, input -> take(user, input) }),
    DROP("drop", hashSetOf("drop", "release", "let", "blurt"), { user, input -> drop(user, input) }),
    GO("go", hashSetOf("go", "move", "walk", "run", "travel", "proceed", "shift"), { user, input -> move(user, input) }),
    BREAK("break", hashSetOf("break", "crack", "snap", "quash"), { user, input -> crack(user, input) });

    fun act(user: User, input: UserInput): Boolean = action(user, input)

    private fun take(user: User, input: UserInput): Boolean {
        val item = input.embeddableItem(user)
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

    private fun crack(user: User, input: UserInput): Boolean {
        val room = user.currentRoom()
        // Requires at least one breaker object to break something.
        val breakerItem = user.inventory.firstOrNull { it is BreakerItem }
        val item = input.breakableItem(user)
        if (breakerItem != null) {
            if (item != null && room is MutableRoom && room.remove(item)) {
                var itemsInside: Collection<Item> = item.inside()
                room.add(itemsInside)
                return true;
            }
        }
        println("Cannot break ${item?.name} with ${breakerItem?.name}")
        return false
    }
}
