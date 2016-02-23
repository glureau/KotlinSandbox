package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.impl

import com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content.Item

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

    override fun narrative(): String {
        return narrative
    }
}
