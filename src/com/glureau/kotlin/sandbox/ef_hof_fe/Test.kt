package com.glureau.kotlin.sandbox.ef_hof_fe

import java.util.*

/**
 * Created by Greg on 24/01/2016.
 */
infix fun Date.toGreg(func: Date.() -> String) {
    print("to....");
    print(func());
    print("... greg");
}

object Test {
    @JvmStatic fun main(args: Array<String>) {
        var d = Date()
        d toGreg { toString() }
    }
}
