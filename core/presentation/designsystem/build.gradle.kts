plugins {
    alias(libs.plugins.runique.android.library.compose)
}

android {
    namespace = "com.example.core.presentation.designsystem"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.graphics)
    debugImplementation(libs.androidx.compose.ui.tooling)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.material3)
}