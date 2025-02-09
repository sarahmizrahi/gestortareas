plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.proyecto_smr"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.proyecto_smr"
        minSdk = 34
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)


    // Retrofit for HTTP requests
    implementation (libs.retrofit)

    // Gson converter for Retrofit (convert JSON to Kotlin objects)
    implementation (libs.converter.gson)
    // OkHttp: Cliente HTTP para Retrofit con soporte para interceptores
    implementation (libs.okhttp)

    // HttpLoggingInterceptor: Utilizado para registrar las solicitudes y respuestas HTTP (útil para depuración)
    implementation(libs.logging.interceptor)

    //Navegacion entre pantallas
    val nav_version = "2.8.6"
    implementation (libs.androidx.navigation.compose)

    // Coil
    implementation (libs.coil.compose)


    // Para ViewModel de Jetpack Compose
    implementation (libs.androidx.hilt.navigation.compose)
}
