plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
//    alias(libs.plugins.kotlin.compose)

    // Add the Google services Gradle plugin
    //todo fireBase
    id("com.google.gms.google-services")

    //ksp
    alias(libs.plugins.ksp)

    // kotlin 2.0
    alias(libs.plugins.compose.compiler)

}

android {
    namespace = "net.developermaster.timejob"
    compileSdk = 35

    defaultConfig {
        applicationId = "net.developermaster.timejob"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    /* //forma antiga compose
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    */
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.material3)
    implementation(libs.ui)


    // fireBase
    implementation(libs.firebase.firestore.ktx)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    // navigation
    implementation(libs.androidx.navigation.runtime.ktx)

    //swipe
    implementation (libs.accompanist.swiperefresh)



}