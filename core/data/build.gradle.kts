plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.example.core.data"
}

dependencies {
    implementation(libs.timber)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)

    implementation(projects.core.domain)
    implementation(projects.core.database)
}