package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.user

/**
 *
 * Created by Greg on 20/02/2016.
 */
class CommandInterpreter() {

    val COMMAND_SEPARATOR = " "

    fun prepare(command: String): UserInput {
        var separatorPos = command.trim().indexOf(COMMAND_SEPARATOR)
        var verb = command.trim().substringBefore(COMMAND_SEPARATOR);
        var directObject: String? = null
        if (separatorPos > 0) {
            directObject = command.trim().substringAfter(COMMAND_SEPARATOR);
        }
        return  UserInput(verb, directObject)
    }
}
