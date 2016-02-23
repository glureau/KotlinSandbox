package com.glureau.kotlin.sandbox.dojos.dungeon.content

/**
 * A chamber in the dungeon.
 * Created by Greg on 24/01/2016.
 */
interface Room {
    val narrative: String
    val items: MutableList<Item>
    val doors: MutableList<Door>
}

