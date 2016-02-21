package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content

/**
 *
 * Created by Greg on 24/01/2016.
 */
data class ItemImpl(
        val name: String = "unnamed",
        val narrative: String = "undefined") : Item {
    override fun name(): String {
        return name
    }
}