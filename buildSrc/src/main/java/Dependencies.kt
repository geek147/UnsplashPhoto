/**
 * To define version
 */
object Versions {

    const val gradlePlugin = "7.0.0"
    const val timber = "4.7.1"
    const val appCompat = "1.3.0"
    const val material = "1.3.0"
    const val constraintLayout = "1.1.3"
    const val jUnit = "4.12"
    const val kotlin = "1.5.21"
    const val hilt = "2.37"
    const val lifecycle = "2.4.0-rc01"
    const val arch = "2.1.0"
    const val core_ktx = "1.6.0"
    const val app_compat = "1.3.1"
    const val nav_version = "2.3.5"
}

/**
 * To define plugins
 */
object BuildPlugins {
    const val android_gradle = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hilt_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

/**
 * To define dependencies
 */
object Deps {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    const val core_ktx = "androidx.core:core-ktx:1.6.0"
    const val app_compat = "androidx.appcompat:appcompat:1.3.1"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:2.1.1"
    const val lifecycle_extension = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val junit = "junit:junit:4.13.2"
    const val android_material = "com.google.android.material:material:1.4.0"
    const val multidex = "androidx.multidex:multidex:2.0.1"
    const val legacy_support = "androidx.legacy:legacy-support-v4:1.0.0"
    // ViewModel
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    // LiveData
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    // Lifecycles only (without ViewModel or LiveData)
    const val runtime_ktxt = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    // livedata_core
    const val livedata_core = "androidx.lifecycle:lifecycle-livedata-core-ktx:2.3.1"

    // retrofit2
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:2.9.0"
    const val retrofit_logging = "com.squareup.okhttp3:logging-interceptor:4.9.1"

    // moshi
    const val moshi = "com.squareup.moshi:moshi-kotlin:1.12.0"
    const val moshi_converter = "com.squareup.retrofit2:converter-moshi:2.9.0"
    const val moshi_codegen = "com.squareup.moshi:moshi-kotlin-codegen:1.12.0"

    // coroutines
    const val coroutine_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1"
    const val coroutine_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1"

    // dagger hilt
    const val dagger_hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val dagger_hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val dagger_hilt_view_model = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
    const val hilt_compiler = "androidx.hilt:hilt-compiler:1.0.0"

    // recyclerview
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"

    // card view
    const val card_view = "androidx.cardview:cardview:1.0.0"

    // test
    const val junit_test = "androidx.test.ext:junit:1.1.3"
    const val espresso = "androidx.test.espresso:espresso-core:3.4.0"

    // optional - Test helpers for LiveData
    const val core_test = "androidx.arch.core:core-testing:${Versions.arch}"

    const val coroutine_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0"
    const val mockk = "io.mockk:mockk:1.11.0"

    const val nav_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    const val nav_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"
}
