package com.glureau.kotlin.sandbox.ef_hof_fe.dungeon.content

/**
 * A chamber in the dungeon.
 * Created by Greg on 24/01/2016.
 */
interface Room {
    val name: String
    val narrative: String
    val items: MutableList<Item>
    val doors: MutableList<Door>
}

