import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.example.appinmobiliaria"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.appinmobiliaria"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "API_URL_BASE",
            "\"${localProperties.getProperty("API_URL_BASE", "URL_POR_DEFECTO_SI_NO_EXISTE")}\""
        )
        buildConfigField(
            "String",
            "API_URL_BASE_PROFE",
            "\"${localProperties.getProperty("API_URL_BASE_PROFE", "URL_POR_DEFECTO_SI_NO_EXISTE")}\""
        )
        buildConfigField(
            "String",
            "GOOGLE_MAPS_API_KEY",
            "\"${localProperties.getProperty("GOOGLE_MAPS_API_KEY", "KEY_POR_DEFECTO")}\""
        )
        buildConfigField(
            "String",
            "TELEFONO",
            "\"${localProperties.getProperty("TELEFONO", "URL_POR_DEFECTO_SI_NO_EXISTE")}\""
        )
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.annotation)
    implementation(libs.activity)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)
    implementation(libs.legacy.support.v4)
    implementation(libs.play.services.maps)
    implementation(libs.recyclerview)
    implementation(libs.glide)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}