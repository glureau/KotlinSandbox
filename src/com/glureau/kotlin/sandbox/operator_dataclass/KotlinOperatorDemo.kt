package com.glureau.kotlin.sandbox.operator_dataclass

object KotlinOperatorDemo {

    internal data class Vector2d(val x: Int, val y: Int) {
        operator fun plus(other: Vector2d) = Vector2d(x + other.x, y + other.y)
        operator fun minus(other: Vector2d) = Vector2d(x - other.x, y - other.y)
    }

    @JvmStatic fun main(args: Array<String>) {
        val vec1 = Vector2d(10, 5)
        val vec2 = Vector2d(2, 4)
        val vec3 = Vector2d(5, 4)
        println(vec1 + vec2 - vec3)
        println((vec1 + vec2 - vec3) == Vector2d(7, 5))
    }
}
