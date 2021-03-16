plugins {
    `android-application`
}

dependencies {
    implementation(Libraries.Jetpack.core)
    implementation(Libraries.Jetpack.activity)
    implementation(Libraries.Jetpack.fragment)
    implementation(Libraries.Jetpack.material)
    implementation(Libraries.Jetpack.constraintLayout)
    testImplementation(Libraries.junit)
    androidTestImplementation(Libraries.Jetpack.junit)
    androidTestImplementation(Libraries.Jetpack.Espresso.core)
}
