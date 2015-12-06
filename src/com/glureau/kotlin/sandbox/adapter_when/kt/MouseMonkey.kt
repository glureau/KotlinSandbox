package com.glureau.kotlin.sandbox.adapter_when.kt;

import java.util.*


/**
 * Random inputs
 */
class MouseMonkey(listener: MouseListener) {

    private val timer: Timer

    init {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val rand = Random().nextInt(5)
                when (rand) {
                    0 -> listener.mouseClicked(MouseEvent())
                    1 -> listener.mouseEntered(MouseEvent())
                    2 -> listener.mouseExited(MouseEvent())
                    3 -> listener.mousePressed(MouseEvent())
                    4 -> listener.mouseReleased(MouseEvent())
                }
            }
        }, 0, 100)
    }

    fun stop() {
        timer.cancel()
    }
}
