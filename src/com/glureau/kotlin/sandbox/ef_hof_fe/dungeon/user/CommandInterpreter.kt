package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Direction
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Room
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.EmbeddableItem

/**
 *
 * Created by Greg on 20/02/2016.
 */
class CommandInterpreter() {

    fun interpret(user: User, command: String): Boolean {
        var verb = command.substringBefore(" ");
        var directObject = command.substringAfter(" ");

        var currentRoom = user.currentRoom()
        var (itemScore, itemSelected) = searchClosestEmbeddableItem(currentRoom, directObject)
        var (dirScore, dirSelected) = searchClosestDirection(directObject)


        var action = UserAction.values().minBy { searchClosestString(it.actions, verb).first }
        if (action != null) {
            var actionScore = searchClosestString(action.actions, verb).first;
            if (actionScore <= 1) {
                action.act(user, currentRoom, itemSelected, dirSelected)
//                println("action: $action ($actionScore) / item: $itemSelected ($itemScore) / direction: $dirSelected ($dirScore)")
                return true
            }
        }

        return false
    }

    private fun searchClosestEmbeddableItem(currentRoom: Room, itemName: String): Pair<Int, EmbeddableItem?> {
        if (currentRoom.items.isNotEmpty()) {
            val item = currentRoom.items.filter { it is EmbeddableItem }.minBy { levenshteinCaseInsensitive(it.name(), itemName) } as EmbeddableItem
            return Pair(levenshteinCaseInsensitive(item.name(), itemName), item)
        }
        return Pair(Int.MAX_VALUE, null)
    }

    private fun searchClosestDirection(dirName: String): Pair<Int, Direction?> {
        var dir = Direction.values().minBy { levenshteinCaseInsensitive(it.name, dirName) }
        if (dir != null)
            return Pair(levenshteinCaseInsensitive(dir.name, dirName), dir)
        return Pair(Int.MAX_VALUE, null)
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

    private fun levenshteinCaseInsensitive(lhs: String, rhs: String): Int {
        return levenshtein(lhs.toLowerCase(), rhs.toLowerCase())
    }

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
}