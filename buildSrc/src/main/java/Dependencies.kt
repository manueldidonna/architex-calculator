@file:Suppress("SpellCheckingInspection", "unused")

object Build {
    const val minSdk = 21
    const val targetSdk = 28
    const val compileSdk = 28
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Modules {
    const val conductorArch = ":conductor-arch"
    const val mathEvaluator = ":math-evaluator"
    const val calculator = ":calculator"
    const val history = ":history"
    const val settings = ":settings"
    const val core = ":core"
    const val database =":database"
}

object GradlePlugins {
    const val butterKnifePlugin = "com.jakewharton:butterknife-gradle-plugin:${Versions.butterKnife}"
    const val versionsPlugin = "com.github.ben-manes:gradle-versions-plugin:0.21.0"
    const val sqldelightPlugin = "com.squareup.sqldelight:gradle-plugin:${Versions.sqldelight}"
}

private object Versions {
    // Jetbrains
    const val kotlin = "1.3.41"
    const val coroutines = "1.2.0"
    // Android
    const val materialComponents = "1.1.0-alpha06"
    const val conductor = "3.0.0-rc1"
    const val materialDialogs = "3.1.0"
    const val rxkPrefs = "1.2.5"
    // AndroidX
    const val coreKtx = "1.0.1"
    const val appCompat = "1.1.0-alpha05"
    const val constraintLayout = "1.1.3"
    const val work = "2.1.0"
    // JakeWharton
    const val butterKnife = "10.1.0"
    const val timber = "4.7.1"
    const val threetenabp = "1.2.0"
    // AirBnb
    const val epoxy = "3.6.0"
    // Rx
    const val rxKotlin = "2.3.0"
    const val rxAndroid = "2.1.1"
    // Koin
    const val koin = "2.0.0-rc-2"
    // Square
    const val sqldelight ="1.1.4"
}

object Jetbrains {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object Android {
    const val materialComponents = "com.google.android.material:material:${Versions.materialComponents}"
    const val conductor = "com.bluelinelabs:conductor:${Versions.conductor}"
    const val conductorSupport = "com.bluelinelabs:conductor-support:${Versions.conductor}"
    const val viewPagerDots = "com.tbuonomo.andrui:viewpagerdotsindicator:3.0.3"
    const val materialDialogsCore = "com.afollestad.material-dialogs:core:${Versions.materialDialogs}"
    const val materialDialogsBottom = "com.afollestad.material-dialogs:bottomsheets:${Versions.materialDialogs}"
    const val rxkPrefs = "com.afollestad:rxkprefs:${Versions.rxkPrefs}"
}

object AndroidX {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val workManager = "androidx.work:work-runtime-ktx:${Versions.work}"
}

object JakeWharton {
    const val threetenabp = "com.jakewharton.threetenabp:threetenabp:${Versions.threetenabp}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val butterKnife = "com.jakewharton:butterknife:${Versions.butterKnife}"
    const val butterKnifeCompiler = "com.jakewharton:butterknife-compiler:${Versions.butterKnife}"
}

object AirBnb {
    const val epoxy = "com.airbnb.android:epoxy:${Versions.epoxy}"
    const val epoxyProcessor = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"
}

object Rx {
    const val kotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    const val android = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
}

object Koin {
    const val core = "org.koin:koin-core:${Versions.koin}"
    const val android = "org.koin:koin-android:${Versions.koin}"
    const val androidScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val unitTest = "org.koin:koin-test:${Versions.koin}"
    const val ext = "org.koin:koin-core-ext:${Versions.koin}"
}

object Square {
    const val sqldelightJvm = "com.squareup.sqldelight:sqlite-driver:${Versions.sqldelight}"
    const val sqldelightAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqldelight}"
    const val sqldelightRxJava = "com.squareup.sqldelight:rxjava2-extensions:${Versions.sqldelight}"
}