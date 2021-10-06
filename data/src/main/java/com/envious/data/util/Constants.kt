package com.envious.data.util

object Constants {
    const val GENERAL_NETWORK_ERROR = "Something went wrong, please try again."
    const val COLLECTION_DEFAULT_ID: Long = 2423569
    const val COLLECTION_DEFAULT_ORIENTATION = "portrait"
}

enum class Filter {
    black_and_white,
    black,
    white,
    yellow,
    orange,
    red,
    purple,
    magenta,
    green,
    teal,
    blue
}

enum class Sort {
    relevant,
    latest
}

enum class Orientation {
    landscape,
    portrait,
    squarish
}
