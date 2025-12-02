plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.example.run.location"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.google.android.gms.play.services.location)

    implementation(projects.core.domain)
    implementation(projects.run.domain)
}