package com.glureau.kotlin.sandbox.dojos.dungeon.content

/**
 *
 * Created by Greg on 21/02/2016.
 */
enum class Direction {
    NORTH {
        override fun opposite(): Direction {
            return SOUTH
        }
    },
    EAST {
        override fun opposite(): Direction {
            return WEST
        }
    },
    SOUTH {
        override fun opposite(): Direction {
            return NORTH
        }
    },
    WEST {
        override fun opposite(): Direction {
            return EAST
        }
    };

    abstract fun opposite(): Direction
}
