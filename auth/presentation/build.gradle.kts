plugins {
    alias(libs.plugins.runique.android.feature.ui)
}

android {
    namespace = "com.example.auth.presentation"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}