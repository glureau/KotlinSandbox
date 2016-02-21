package com.glureau.kotlin.sandbox.bad_local_variable_type

/**
 *
 * Created by Greg on 21/02/2016.
 */
object BadLocalVariableType {
    @JvmStatic fun main(args: Array<String>) {
        var foo : Foo?
        if (args.size == 0) {
            foo = Foo()
        } else {
            error("too much args")
        }
        if (foo != null)
            doNothing();

        println("hello")
    }

    private fun doNothing(){}
}

class Foo