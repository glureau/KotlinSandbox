package com.glureau.kotlin.sandbox.adapter_when.kt

/**
 * @see com.glureau.kotlin.sandbox.adapter_when.java.MouseListener
 */
interface MouseListener {
    fun mouseClicked(e: MouseEvent) {}
    fun mousePressed(e: MouseEvent) {}
    fun mouseReleased(e: MouseEvent) {}
    fun mouseEntered(e: MouseEvent) {}
    fun mouseExited(e: MouseEvent) {}
}
