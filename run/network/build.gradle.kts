plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.example.run.network"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.core.domain)
    implementation(projects.core.data)
}