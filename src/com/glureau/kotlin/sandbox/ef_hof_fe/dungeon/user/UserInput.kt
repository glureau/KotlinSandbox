package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Direction
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.BreakableItem
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.EmbeddableItem
import java.util.*

/**
 *
 * Created by Greg on 22/02/2016.
 */
data class UserInput(val verb: String, val directObject: String?) {
    companion object {
        val LEVENSHTEIN_THRESHOLD = 1
    }

    private val userAction: UserAction? = UserAction.values().minByWithLimit({ searchClosestString(it.actionNames, verb).first }, LEVENSHTEIN_THRESHOLD)

    fun act(user: User): Boolean {
        if (userAction != null) {
            return userAction.act(user, this)
        }
        return false
    }

    fun direction(): Direction? {
        directObject ?: return null
        return Direction.values().minByWithLimit({ levenshteinCaseInsensitive(it.name, directObject) }, LEVENSHTEIN_THRESHOLD)
    }

    fun embeddableItem(user: User): EmbeddableItem? {
        return typedItem<EmbeddableItem>(user)
    }

    fun embeddableItemInRoom(user: User): EmbeddableItem? {
        return typedItemInRoom<EmbeddableItem>(user)
    }

    fun embeddableItemInInventory(user: User): EmbeddableItem? {
        return typedItemInInventory<EmbeddableItem>(user)
    }

    fun breakableItem(user: User): BreakableItem? {
        return typedItem<BreakableItem>(user)
    }

    fun breakableItemInRoom(user: User): BreakableItem? {
        return typedItemInRoom<BreakableItem>(user)
    }

    fun breakableItemInInventory(user: User): BreakableItem? {
        return typedItemInInventory<BreakableItem>(user)
    }


    private inline fun <reified T> typedItem(user: User): T? {
        directObject ?: return null
        return items(user).filter { it is T }.toTypedArray().minByWithLimit({ levenshteinCaseInsensitive(it.name, directObject) }, LEVENSHTEIN_THRESHOLD) as T?
    }

    private inline fun <reified T> typedItemInRoom(user: User): T? {
        directObject ?: return null
        return user.currentRoom()?.items?.filter { it is T }?.toTypedArray()?.minByWithLimit({ levenshteinCaseInsensitive(it.name, directObject) }, LEVENSHTEIN_THRESHOLD) as T?
    }

    private inline fun <reified T> typedItemInInventory(user: User): T? {
        directObject ?: return null
        return user.inventory.filter { it is T }.toTypedArray().minByWithLimit({ levenshteinCaseInsensitive(it.name, directObject) }, LEVENSHTEIN_THRESHOLD) as T?
    }

    fun item(user: User): Item? {
        directObject ?: return null
        return items(user).toTypedArray().minByWithLimit({ levenshteinCaseInsensitive(it.name, directObject) }, LEVENSHTEIN_THRESHOLD)
    }

    fun items(user: User): List<Item> {
        var items = ArrayList<Item>(user.inventory.size + (user.currentRoom()?.items?.size ?: 0))

        // User inventory
        items.addAll(user.inventory)

        // Room items
        var room = user.currentRoom();
        if (room != null) {
            items.addAll(room.items)
        }

        return items
    }

    private fun searchClosestString(strings: Collection<String>, actionName: String): Pair<Int, String?> {
        if (strings.isNotEmpty()) {
            val str = strings.minBy { levenshteinCaseInsensitive(it, actionName) }
            if (str != null) {
                return Pair(levenshteinCaseInsensitive(str, actionName), str)
            }
        }
        return Pair(Int.MAX_VALUE, null)
    }

    fun levenshteinCaseInsensitive(lhs: String, rhs: String): Int {
        return levenshtein(lhs.toLowerCase(), rhs.toLowerCase())
    }

    // https://gist.github.com/ademar111190/34d3de41308389a0d0d8
    private fun levenshtein(lhs: CharSequence, rhs: CharSequence): Int {
        val lhsLength = lhs.length
        val rhsLength = rhs.length

        var cost = Array(lhsLength) { it }
        var newCost = Array(lhsLength) { 0 }

        for (i in 1..rhsLength - 1) {
            newCost[0] = i

            for (j in 1..lhsLength - 1) {
                val match = if (lhs[j - 1] == rhs[i - 1]) 0 else 1

                val costReplace = cost[j - 1] + match
                val costInsert = cost[j] + 1
                val costDelete = newCost[j - 1] + 1

                newCost[j] = Math.min(Math.min(costInsert, costDelete), costReplace)
            }

            val swap = cost
            cost = newCost
            newCost = swap
        }

        return cost[lhsLength - 1]
    }

    // Copied from Kotlin minBy, but filter on the min value.
    // We could also return a Pair<R,T> in a similar minBy methods to be retrieve the minValue at the end
    fun <T, R : Comparable<R>> Array<out T>.minByWithLimit(selector: (T) -> R, limit: R): T? {
        if (isEmpty()) return null
        var minElem = this[0]
        var minValue = selector(minElem)
        for (i in 1..lastIndex) {
            val e = this[i]
            val v = selector(e)
            if (minValue > v) {
                minElem = e
                minValue = v
            }
        }
        if (minValue <= limit)
            return minElem
        return null
    }

}