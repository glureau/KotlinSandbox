package com.glureau.kotlin.sandbox.adapter_when.kt

class MouseEvent

class Vector2d(val x: Int, val y: Int) {
    operator fun plus(other: Vector2d) = Vector2d(x + other.x, y + other.y)

    operator fun minus(other: Vector2d) = Vector2d(x - other.x, y - other.y)
}