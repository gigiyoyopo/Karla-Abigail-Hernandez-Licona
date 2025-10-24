plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.gigifai"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.gigifai"
        minSdk = 21
        targetSdk = 36
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

}

dependencies {
    dependencies {
        // --- Compose core ---
        implementation(platform("androidx.compose:compose-bom:2024.05.00"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.material:material")
        implementation("androidx.compose.ui:ui-tooling-preview")
        debugImplementation("androidx.compose.ui:ui-tooling")

        // --- Navigation Compose ---
        implementation("androidx.navigation:navigation-compose:2.7.7")

        // --- Palette (para colores dominantes de portadas) ---
        implementation("androidx.palette:palette:1.0.0")

        // --- MediaPlayer compatibilidad (ya viene con Android SDK, pero por si acaso) ---
        implementation("androidx.media:media:1.6.0")

        // --- Toasts y coroutines (para async) ---
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
        implementation("androidx.compose.material:material:1.5.3")
        implementation("androidx.compose.material:material-icons-extended:1.5.3")

        // 👇 Mantén tus dependencias que ya estaban abajo
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)
        debugImplementation(libs.androidx.compose.ui.tooling)
        debugImplementation(libs.androidx.compose.ui.test.manifest)
    }}