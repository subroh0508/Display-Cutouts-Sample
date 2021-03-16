object Libraries {
    object Jetpack {
        private const val coreVersion = "1.3.2"
        private const val activityVersion = "1.2.0"
        private const val fragmentVersion = "1.3.0"
        private const val constraintLayoutVersion = "2.0.4"
        private const val materialVersion = "1.3.0"

        const val core = "androidx.core:core-ktx:$coreVersion"
        const val activity = "androidx.activity:activity-ktx:$activityVersion"
        const val fragment = "androidx.fragment:fragment-ktx:$fragmentVersion"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
        const val material = "com.google.android.material:material:$materialVersion"

        private const val junitVersion = "1.1.2"
        const val junit = "androidx.test.ext:junit:$junitVersion"

        object Espresso {
            private const val version = "3.3.0"
            const val core = "androidx.test.espresso:espresso-core:$version"
        }
    }

    private const val junitVersion = "4.+"
    const val junit = "junit:junit:$junitVersion"
}
