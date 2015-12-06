package com.glureau.kotlin.sandbox.enums

/** [Enum classes](https://kotlinlang.org/docs/reference/enum-classes.html) */
object KotlinEnumDemo {

    enum class REQUEST_STATUS { VALID, INVALID }

    enum class HTTP_ADVANCED_STATUS (val code: Int, val message: String, val meaning: String) {
        RESPONSE_200(200, "OK", "Standard response for successful HTTP requests."),
        RESPONSE_201(201, "Created", "The request has been fulfilled and resulted in a new resource being created."),
        RESPONSE_404(404, "Not Found", "The requested resource could not be found but may be available again in the future."),
        RESPONSE_418(418, "I'm a teapot", "This code was defined in 1998 as one of the traditional IETF April Fools' jokes, in RFC 2324")
    }

    @JvmStatic fun main(args: Array<String>) {
        for (st in REQUEST_STATUS.values) {
            println(st)
        }
        for (st in HTTP_ADVANCED_STATUS.values) {
            println("${st.code} -> ${st.meaning}")
        }
    }
}
