package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Room
import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.interaction.ActionnableItem

/**
 *
 * Created by Greg on 20/02/2016.
 */
class CommandInterpreter() {

    fun interpret(user: User, command: String): Boolean {
        var actionName = command.substringBefore(" ");
        var itemName = command.substringAfter(" ");

        var currentRoom = user.currentRoom()
        var (itemScore, itemSelect) = searchClosestItem(currentRoom, itemName)
        //println("item> score:$itemScore command:$itemSelect");
        if (itemSelect != null && itemScore <= 1) {
            var (actionScore, actionCommand) = searchClosestString(itemSelect.actions(), actionName);
            //println("action> score:$actionScore command:$actionCommand");
            if (actionScore <= 1) {
                currentRoom.remove(itemSelect)
                user.take(itemSelect)
                return true
            }
        }
        return false
    }

    private fun searchClosestItem(currentRoom: Room, itemName: String): Pair<Int, ActionnableItem?> {
        var min: Int = Int.MAX_VALUE
        var itemSelected: ActionnableItem? = null
        for (item in currentRoom.items) {
            if (item is ActionnableItem) {
                var dist = levenshtein(item.name(), itemName)
                if (min > dist) {
                    min = dist
                    itemSelected = item
                }
            }
        }
        return Pair(min, itemSelected)
    }

    private fun searchClosestString(collec: Collection<String>, actionName: String): Pair<Int, String?> {
        var min: Int = Int.MAX_VALUE
        var closest: String? = null;
        for (action in collec) {
            var dist = levenshtein(action, actionName)
            if (min > dist) {
                min = dist
                closest = action
            }
        }
        return Pair(min, closest)
    }

    fun levenshtein(lhs: CharSequence, rhs: CharSequence): Int {
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