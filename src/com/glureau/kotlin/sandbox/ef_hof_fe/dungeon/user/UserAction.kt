package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Direction
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.builder.MutableRoom
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Room
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.EmbeddableItem

/**
 *
 * Created by Greg on 21/02/2016.
 */
enum class UserAction(val defaultAction: String, val actions: Set<String>, val act: (user: User, room: Room?, item: Item?, dir: Direction?) -> Boolean) {
    TAKE("take", hashSetOf("take", "get"),
            { user, room, item, dir ->
                if (item != null && item is EmbeddableItem && room is MutableRoom) {
                    room.remove(item)
                    user.take(item)
                    true
                } else {
                    false
                }
            }),
    MOVE("move", hashSetOf("move", "go", "walk", "run"),
            { user, room, item, dir ->
                val door = room?.doors?.filter { (it.dir == dir && it.left == room) || (it.dir.opposite() == dir && it.right == room) }?.singleOrNull()
                if (door == null) {
                    println("Too much doors or no door at all in this direction")
                    false
                } else {
                    user.use(door)
                }
            });
}