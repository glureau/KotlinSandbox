package com.glureau.kotlin.sandbox.dojos.dungeon.content.impl

import com.glureau.kotlin.sandbox.dojos.dungeon.content.Item

/**
 *
 * Created by Greg on 24/01/2016.
 */
data class ItemImpl(
        override val name: String = "unnamed",
        override val narrative: String = "undefined") : Item {
}
