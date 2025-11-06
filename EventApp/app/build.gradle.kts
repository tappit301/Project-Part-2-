plugins {
    id ("com.android.application")
}

android {
    namespace = "com.example.eventapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.eventapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true // ✅ Enable Android resources for Robolectric
        }
    }
}

dependencies {
    // AndroidX and Material
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.12.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.fragment:fragment:1.6.2")

    // Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.8.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.8.3")
    implementation(libs.ext.junit)

    // Testing libraries
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.robolectric:robolectric:4.12")
    testImplementation ("androidx.test:core:1.6.1")
    testImplementation ("androidx.navigation:navigation-testing:2.8.3")

    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation ("org.mockito:mockito-android:5.5.0")

    debugImplementation ("androidx.fragment:fragment-testing:1.6.2")
}
