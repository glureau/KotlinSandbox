package com.glureau.kotlin.sandbox.adapter_when

import com.glureau.kotlin.sandbox.adapter_when.kt.MouseMonkey
import com.glureau.kotlin.sandbox.adapter_when.kt.MouseEvent
import com.glureau.kotlin.sandbox.adapter_when.kt.MouseListener

/**
 * Created by Greg on 05/12/2015.
 * @see JavaAdapterDemo
 */
object KotlinAdapterDemo {

    @JvmStatic fun main(args: Array<String>) {
        val listener :MouseListener = object : MouseListener{
            override fun mousePressed(e: MouseEvent) {
                println("Pressed")
            }

            override fun mouseReleased(e: MouseEvent) {
                println("Released")
            }
        }

        val monkey = MouseMonkey(listener)
        Thread.sleep(1000)
        monkey.stop()
    }
}
