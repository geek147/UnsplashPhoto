package com.envious.data.util

object Constants {
    const val GENERAL_NETWORK_ERROR = "Something went wrong, please try again."
    const val COLLECTION_DEFAULT_ID: Long = 2423569
    const val COLLECTION_DEFAULT_ORIENTATION = "portrait"
    const val SHARED_KEY_SORT_BY = "KEY_SORT_BY"
    const val SHARED_KEY_COLOR = "KEY_COLOR"
    const val SHARED_KEY_ORIENTATION = "KEY_ORIENTATION"
}

enum class Filter(val color: String) {
    black_and_white("Black And White"),
    black("Black"),
    white("WHite"),
    yellow("Yellow"),
    orange("Orange"),
    red("Red"),
    purple("Purple"),
    magenta("Magenta"),
    green("Green"),
    teal("Teal"),
    blue("Blue"),
    unknown(""),
    any("Any Color")
}

enum class Sort(val type: String) {
    relevant("Relevance"),
    latest("Newest"),
    unknown(""),
}

enum class Orientation(val type: String) {
    landscape("Landscape"),
    portrait("Portrait"),
    squarish("Square"),
    unknown(""),
    any("Any")
}
