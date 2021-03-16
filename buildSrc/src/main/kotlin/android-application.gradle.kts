plugins {
    id("com.android.application")
    kotlin("android")
}

androidBaseExt()
android {
    defaultConfig {
        applicationId = Android.applicationId
        versionCode = Android.versionCode
        versionName = Android.versionName
    }

    buildTypes {
        getByName(BUILD_TYPE_RELEASE) {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        exclude("META-INF/*")
    }
}
