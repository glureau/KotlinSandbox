package com.glureau.kotlin.sandbox.enums;

public class JavaEnumDemo {

    enum REQUEST_STATUS {VALID, INVALID}

    enum HTTP_ADVANCED_STATUS {
        RESPONSE_200(200, "OK", "Standard response for successful HTTP requests."),
        RESPONSE_201(201, "Created", "The request has been fulfilled and resulted in a new resource being created."),
        RESPONSE_404(404, "Not Found", "The requested resource could not be found but may be available again in the future."),
        RESPONSE_418(418, "I'm a teapot", "This code was defined in 1998 as one of the traditional IETF April Fools' jokes, in RFC 2324");

        final int code;
        final String message;
        final String meaning;
        HTTP_ADVANCED_STATUS(int code, String message, String meaning) {
            this.code = code;
            this.message = message;
            this.meaning = meaning;
        }
    }

    public static void main(String[] args) {
        for (REQUEST_STATUS st : REQUEST_STATUS.values()) {
            System.out.println(st);
        }
        for (HTTP_ADVANCED_STATUS st : HTTP_ADVANCED_STATUS.values()) {
            System.out.println(st.code + " -> " + st.meaning);
        }
    }
}
